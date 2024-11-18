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
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.services.JwtService;
import com.dietideals.dietideals24_25.services.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
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

        Optional<UserEntity> userEntityOpt = userService.findById(UUID.fromString(userId));
        
        if (userEntityOpt.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userEntity = userEntityOpt.get();
            UserDto userDto = new UserDto(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getName(),
                new HashSet<>(roles)
            );

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