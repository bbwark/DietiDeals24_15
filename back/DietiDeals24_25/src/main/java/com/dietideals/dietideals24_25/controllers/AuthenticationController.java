package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.LoginRequest;
import com.dietideals.dietideals24_25.domain.dto.RegistrationDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.services.impl.AuthenticationServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private AuthenticationServiceImpl authenticationService;

    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserEntity registerUserBuyer(@RequestBody RegistrationDto registrationDto){
        return authenticationService.registerUserBuyer(registrationDto.getEmail(), registrationDto.getName(), registrationDto.getSurname(), registrationDto.getPassword(), registrationDto.getAddress(), registrationDto.getZipCode(), registrationDto.getCountry(), registrationDto.getPhoneNumber(), registrationDto.getCreditCards());
    }

    @PostMapping("/login")
    public LoginDto loginUser(@RequestBody LoginRequest loginRequest) {
        return authenticationService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }

}
