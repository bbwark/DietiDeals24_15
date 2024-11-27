package com.dietideals.dietideals24_25.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.RoleService;
import com.dietideals.dietideals24_25.services.UserService;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private Mapper<UserEntity, UserDto> userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    private UserEntity userEntity;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        userDto = new UserDto();
        userDto.setId(userId);
        userDto.setPassword("password");
        userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setPassword("encodedPassword");
    }

    @Test
    void updateUser_UserNotFound() {
        when(userService.exists(userId)).thenReturn(false);

        ResponseEntity<UserDto> response = userController.updateUser(userId.toString(), userDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateUser_ValidUser() {
        when(userService.exists(userId)).thenReturn(true);
        when(userService.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.findByAuthority("BUYER")).thenReturn(Optional.of(new RoleEntity()));
        when(userMapper.mapFrom(any(UserDto.class))).thenReturn(userEntity);
        when(userMapper.mapTo(any(UserEntity.class))).thenReturn(userDto);
        when(userService.save(any(UserEntity.class))).thenReturn(userEntity);

        ResponseEntity<UserDto> response = userController.updateUser(userId.toString(), userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void updateUser_InvalidUser() {
        when(userService.exists(userId)).thenReturn(true);
        when(userService.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.findByAuthority("BUYER")).thenReturn(Optional.of(new RoleEntity()));
        when(userMapper.mapFrom(any(UserDto.class))).thenReturn(userEntity);
        when(userMapper.mapTo(any(UserEntity.class))).thenReturn(userDto);
        when(userService.save(any(UserEntity.class))).thenThrow(new RuntimeException());

        ResponseEntity<UserDto> response = userController.updateUser(userId.toString(), userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateUser_WhenIsSellerTrueAndNoAddress_ShouldSetIsSellerToFalse() {
        userDto.setIsSeller(true);
        when(userService.exists(userId)).thenReturn(true);
        when(userService.findById(userId)).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleService.findByAuthority("BUYER")).thenReturn(Optional.of(new RoleEntity()));
        when(userMapper.mapFrom(any(UserDto.class))).thenReturn(userEntity);
        when(userMapper.mapTo(any(UserEntity.class))).thenReturn(userDto);
        when(userService.save(any(UserEntity.class))).thenReturn(userEntity);

        ResponseEntity<UserDto> response = userController.updateUser(userId.toString(), userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().getIsSeller());
        assertEquals(false, userEntity.getAuthorities().stream()
                .anyMatch(role -> "SELLER".equals(role.getAuthority())));
    }
}