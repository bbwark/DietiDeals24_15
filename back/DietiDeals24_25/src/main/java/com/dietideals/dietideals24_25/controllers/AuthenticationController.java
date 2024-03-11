package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.RegistrationDto;
import com.dietideals.dietideals24_25.domain.entities.ApplicationUser;
import com.dietideals.dietideals24_25.services.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDto registrationDto){
        return authenticationService.registerUser(registrationDto.getUsername(), registrationDto.getPassword());
    }

    @PostMapping("/login")
    public LoginDto loginUser(@RequestBody RegistrationDto registrationDto){
        return authenticationService.loginUser(registrationDto.getUsername(), registrationDto.getPassword());
    }

}
