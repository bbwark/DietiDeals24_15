package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.Role;
import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.repositories.RoleRepository;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    public UserEntity registerUser(String email, String password){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new UserEntity(0,email,encodedPassword, authorities));

    }

    public LoginDto loginUser(String email, String password){

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = tokenService.generateJwt(authentication);
            return new LoginDto((UserEntity) userRepository.findByEmail(email).get(), token);
        } catch (AuthenticationException e){
            return new LoginDto(null, "");
        }
    }

}
