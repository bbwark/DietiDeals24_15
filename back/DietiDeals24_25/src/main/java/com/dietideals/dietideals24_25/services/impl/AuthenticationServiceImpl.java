package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.services.AuthenticationService;
import com.dietideals.dietideals24_25.services.JwtService;
import com.dietideals.dietideals24_25.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private Mapper<UserEntity, UserDto> userMapper;
    private JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, UserService userService,
            PasswordEncoder passwordEncoder,
            Mapper<UserEntity, UserDto> userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public LoginDto loginUser(String email, String password) {
        try {

            String encodedPassword = passwordEncoder.encode(password);
            UserEntity user = userService.findUserByEmailPassword(email, encodedPassword)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            UserDto userDto = userMapper.mapTo(user);

            String token = jwtService.generateToken(userDto.getId().toString(), userDto.getAuthorities());
            UserEntity userEntity = userRepository.findByEmail(email).get();
            Optional<UserDto> userResponse = Optional.ofNullable(userMapper.mapTo(userEntity));
            return new LoginDto(userResponse, token);
        } catch (Exception e) {
            return new LoginDto(null, "");
        }

    }
}
