package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.LoginRequest;
import com.dietideals.dietideals24_25.domain.dto.RegistrationDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.services.impl.AuthenticationServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private Mapper<UserEntity, UserDto> userMapper;
    private AuthenticationServiceImpl authenticationService;

    private UserRepository userRepository;

    public AuthenticationController(Mapper<UserEntity, UserDto> userMapper, AuthenticationServiceImpl authenticationService, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/registerUser")
    public UserEntity registerUserBuyer(@RequestBody RegistrationDto registrationDto){
        return authenticationService.registerUserBuyer(registrationDto.getEmail(), registrationDto.getName(), registrationDto.getSurname(), registrationDto.getPassword(), registrationDto.getAddress(), registrationDto.getZipCode(), registrationDto.getCountry(), registrationDto.getPhoneNumber(), registrationDto.getCreditCards());
    }

    @PostMapping("/loginUser")
    public LoginDto loginUser(@RequestBody LoginRequest loginRequest) {
        return authenticationService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/register/google")
    public Optional<UserEntity> registerGoogleAccount(@RequestBody String googleIdToken) throws GeneralSecurityException, IOException {
        UserDto userDto = authenticationService.registerWithGoogle(googleIdToken);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        if(userRepository.findByEmail(userEntity.getEmail()).isEmpty()){
            userRepository.save(userEntity);
        }
        return userRepository.findByEmail(userEntity.getEmail());

    }



}
