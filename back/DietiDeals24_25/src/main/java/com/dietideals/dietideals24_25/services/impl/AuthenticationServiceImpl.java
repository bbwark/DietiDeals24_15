package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.entities.ApplicationUser;
import com.dietideals.dietideals24_25.domain.entities.Role;
import com.dietideals.dietideals24_25.repositories.ApplicationUserRepository;
import com.dietideals.dietideals24_25.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AuthenticationServiceImpl {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServiceImpl tokenService;

    public ApplicationUser registerUser(String username, String password){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return applicationUserRepository.save(new ApplicationUser(UUID.randomUUID(), username, encodedPassword, authorities));
    }

    public LoginDto loginUser(String username, String password){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(authentication);

            return new LoginDto(applicationUserRepository.findByUsername(username).get(), token);

        } catch (AuthenticationException e){
            return  new LoginDto(null, "");
        }

    }

}
