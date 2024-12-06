package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.Country;
import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuthenticationService;
import com.dietideals.dietideals24_25.services.JwtService;
import com.dietideals.dietideals24_25.services.RoleService;
import com.dietideals.dietideals24_25.services.UserService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private Mapper<UserEntity, UserDto> userMapper;
    private JwtService jwtService;
    private RoleService roleService;

    public AuthenticationServiceImpl(UserService userService,
            PasswordEncoder passwordEncoder,
            Mapper<UserEntity, UserDto> userMapper, JwtService jwtService, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.roleService = roleService;
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

    @Override
    public LoginDto loginWithGoogle(String email, String name) {
        try {
            UserEntity user = userService.findUserByEmail(email)
                    .orElse(null);
            if(user != null) {
                UserDto userDto = userMapper.mapTo(user);
                String token = jwtService.generateToken(userDto.getId().toString(), userDto.getAuthorities());
                return new LoginDto(userDto, token);
            } else{
                String emptyString = "";
                UserDto userDto = new UserDto();
                userDto.setEmail(email);
                userDto.setName(name);
                userDto.setPassword(emptyString);
                userDto.setIsSeller(false);
                
                userDto.setBio(Optional.of(emptyString));
                userDto.setAddress(Optional.of(emptyString));
                userDto.setZipcode(Optional.of(emptyString));
                userDto.setCountry(Optional.of(Country.Italy));
                userDto.setPhoneNumber(Optional.of(emptyString));

                Set<RoleEntity> authorities = new HashSet<>();
                RoleEntity buyerRole = roleService.findByAuthority("BUYER")
                        .orElseThrow(() -> new RuntimeException("BUYER role not found"));
                authorities.add(buyerRole);
                UserEntity userEntity = userMapper.mapFrom(userDto);
                userEntity.setAuthorities(authorities);
                UserEntity savedUserEntity = userService.save(userEntity);
                UserDto responseUser = userMapper.mapTo(savedUserEntity);
                String token = jwtService.generateToken(responseUser.getId().toString(), responseUser.getAuthorities());
                return new LoginDto(responseUser, token);
            }
        } catch (Exception e){
            return new LoginDto(null, "");
        }
    }
}
