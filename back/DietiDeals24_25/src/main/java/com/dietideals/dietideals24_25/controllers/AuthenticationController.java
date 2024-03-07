package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.RegistrationDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.services.impl.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody RegistrationDto registrationDto){
        return authenticationService.registerUser(registrationDto.getEmail(),registrationDto.getPassword());
    }

    @PostMapping("/login")
    public LoginDto loginUser(@RequestBody RegistrationDto registrationDto){
        return authenticationService.loginUser(registrationDto.getEmail(), registrationDto.getPassword());
    }

}
