package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private Mapper<UserEntity, UserDto> userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/google")
    public ResponseEntity<?> getOAuthUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return ResponseEntity.ok(oAuth2User.getAttributes());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") UUID id, @RequestBody UserDto userDto) {
        try {
            if (!userService.exists(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userDto.setId(id);
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            } else {
                UserEntity existingUser = userService.findById(id).get();
                userDto.setPassword(existingUser.getPassword());
            }

            boolean hasSellerAuthority = userService.checkAuthorities(id, "SELLER");
            userDto.setIsSeller(hasSellerAuthority);

            UserEntity userEntity = userMapper.mapFrom(userDto);
            UserEntity savedUserEntity = userService.save(userEntity);
            return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") UUID id) {
        try {
            Optional<UserEntity> foundUser = userService.findById(id);
            return foundUser.map(userEntity -> {
                UserDto userDto = userMapper.mapTo(userEntity);
                userDto.setPassword("");
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/info/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("id") UUID id) {
        try {
            Optional<UserEntity> foundUser = userService.findById(id);
            return foundUser.map(userEntity -> {
                UserDto userDto = userMapper.mapTo(userEntity);
                userDto = new UserDto(userDto.getId(), userDto.getName(), userDto.getIsSeller(), userDto.getBio());
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/loginSuccesful")
    public String logInGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        UUID id = oAuth2AuthenticationToken.getPrincipal().getAttribute("id");
        Optional<UserEntity> userEntity = userService.findById(id);
        if (userEntity.isPresent()) {
            return "Benvenuto";
        } else {
            return "Utente non trovato";
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
