package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.domain.entities.Role;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private  TokenServiceImpl tokenService;


    public UserEntity registerUserBuyer(String email, String name, String surname, String password, String address, Integer zipCode, String country, String phoneNumber, List<CreditCardEntity> creditCards){

        boolean isSeller = true;
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        if(address == null || zipCode == null || country == null || phoneNumber == null || creditCards == null){
            isSeller = false;
        }

        return userRepository.save(new UserEntity(UUID.randomUUID(), email, name, surname, encodedPassword, authorities, isSeller, address, zipCode, country, phoneNumber, creditCards));
    }

    public LoginDto loginUser(String email, String password){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(authentication);

            return new LoginDto(userRepository.findByEmail(email), token);

        } catch (AuthenticationException e){
            return  new LoginDto(null,   "");
        }

    }

}
