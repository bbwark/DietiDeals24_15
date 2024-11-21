package com.dietideals.dietideals24_25.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.JwtService;
import com.dietideals.dietideals24_25.services.UserService;
import com.dietideals.dietideals24_25.services.impl.AuthenticationServiceImpl;

class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Mapper<UserEntity, UserDto> userMapper;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private UserEntity userEntity;
    private UserDto userDto;
    private String email;
    private String password;
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        email = "test@example.com";
        password = "password";
        token = "token";
        userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setEmail(email);
        userEntity.setPassword("encodedPassword");
        userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(email);
        userDto.setPassword("encodedPassword");
    }

    @Test
    void loginUser_UserNotFound() {
        when(userService.findUserByEmail(email)).thenReturn(Optional.empty());

        LoginDto result = authenticationService.loginUser(email, password);

        assertNull(result.getUser());
        assertEquals("", result.getJwt());
    }

    @Test
    void loginUser_InvalidPassword() {
        when(userService.findUserByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapTo(userEntity)).thenReturn(userDto);
        when(passwordEncoder.matches(password, userDto.getPassword())).thenReturn(false);
    
        LoginDto result = authenticationService.loginUser(email, password);
    
        assertNull(result.getUser());
        assertEquals("", result.getJwt());
    }

    @Test
    void loginUser_ValidCredentials() {
        when(userService.findUserByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapTo(userEntity)).thenReturn(userDto);
        when(passwordEncoder.matches(password, userDto.getPassword())).thenReturn(true);
        when(jwtService.generateToken(userDto.getId().toString(), userDto.getAuthorities())).thenReturn(token);
        LoginDto loginDto = authenticationService.loginUser(email, password);

        assertEquals(userDto, loginDto.getUser());
        assertEquals(token, loginDto.getJwt());
    }
}