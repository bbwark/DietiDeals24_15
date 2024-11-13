package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.services.AuthenticationService;
import com.dietideals.dietideals24_25.services.UserService;
import com.dietideals.dietideals24_25.utils.jwtUtilities.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private Mapper<UserEntity, UserDto> userMapper;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationServiceImpl(UserRepository userRepository, UserService userService,
            PasswordEncoder passwordEncoder,
            Mapper<UserEntity, UserDto> userMapper, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginDto loginUser(String email, String password) {
        try {

            String encodedPassword = passwordEncoder.encode(password);
            UUID userId = userService.findUserIdByEmailPassword(email, encodedPassword);
            if (userId == null) {
                return new LoginDto(null, "");
            }

            String token = jwtTokenProvider.generateToken(userId.toString());
            UserEntity userEntity = userRepository.findByEmail(email).get();
            Optional<UserDto> userResponse = Optional.ofNullable(userMapper.mapTo(userEntity));
            return new LoginDto(userResponse, token);
        } catch (Exception e) {
            return new LoginDto(null, "");
        }

    }
}
