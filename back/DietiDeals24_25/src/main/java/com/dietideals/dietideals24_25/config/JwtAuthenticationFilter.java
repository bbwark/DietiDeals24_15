package com.dietideals.dietideals24_25.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.services.JwtService;
import com.dietideals.dietideals24_25.utils.UserLoaderHelper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserLoaderHelper userLoaderHelper;

    public JwtAuthenticationFilter(JwtService jwtService, UserLoaderHelper userLoaderHelper) {
        this.jwtService = jwtService;
        this.userLoaderHelper = userLoaderHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String userId = jwtService.extractUserIdFromToken(token);
        List<String> roles = jwtService.extractRolesFromToken(token);

        UserDto userDto = userLoaderHelper.userLoader(userId);
        if (userDto != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDto,
                    null,
                    roles.stream()
                         .map(SimpleGrantedAuthority::new)
                         .collect(Collectors.toList())
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        
        filterChain.doFilter(request, response);
    }
}