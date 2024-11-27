package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuthenticationService;
import com.dietideals.dietideals24_25.services.JwtService;
import com.dietideals.dietideals24_25.services.UserService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private Mapper<UserEntity, UserDto> userMapper;
    private JwtService jwtService;

    public AuthenticationServiceImpl(UserService userService,
            PasswordEncoder passwordEncoder,
            Mapper<UserEntity, UserDto> userMapper, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public LoginDto loginUser(String email, String password) {
        try {

            UserEntity user = userService.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
            UserDto userDto = userMapper.mapTo(user);
            if (!passwordEncoder.matches(password, userDto.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            String token = jwtService.generateToken(userDto.getId().toString(), userDto.getAuthorities());
            return new LoginDto(userDto, token);
        } catch (Exception e) {
            return new LoginDto(null, "");
        }

    }
}
