package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.LoginRequest;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.services.CreditCardService;
import com.dietideals.dietideals24_25.services.RoleService;
import com.dietideals.dietideals24_25.services.UserService;
import com.dietideals.dietideals24_25.services.impl.AuthenticationServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;
    private CreditCardService creditCardService;
    private RoleService roleService;
    private Mapper<CreditCardEntity, CreditCardDto> creditCardMapper;
    private Mapper<UserEntity, UserDto> userMapper;
    private AuthenticationServiceImpl authenticationService;
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, CreditCardService creditCardService,
            RoleService roleService, Mapper<CreditCardEntity, CreditCardDto> creditCardMapper,
            Mapper<UserEntity, UserDto> userMapper, AuthenticationServiceImpl authenticationService,
            UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.roleService = roleService;
        this.creditCardMapper = creditCardMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<UserDto> registerUserBuyer(@RequestBody UserDto user) {
        return new UserController(userService, creditCardService, roleService, userMapper, creditCardMapper, passwordEncoder)
                .createUser(user);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<LoginDto> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(
                    authenticationService.loginUser(loginRequest.getEmail(), loginRequest.getPassword()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginDto(null, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/google")
    public Optional<UserEntity> registerGoogleAccount(@RequestBody String googleIdToken)
            throws GeneralSecurityException, IOException {
        UserDto userDto = authenticationService.registerWithGoogle(googleIdToken);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        if (userRepository.findByEmail(userEntity.getEmail()).isEmpty()) {
            userRepository.save(userEntity);
        }
        return userRepository.findByEmail(userEntity.getEmail());

    }

}
