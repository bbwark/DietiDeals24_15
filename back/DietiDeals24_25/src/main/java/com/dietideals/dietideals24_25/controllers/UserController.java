package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private UserService userService;

    private Mapper<UserEntity, UserDto> userMapper;

    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    public String helloUser(){
        return "Hello user";
    }

    @PostMapping(path = "/users")
    public UserDto createUser(@RequestBody UserDto user){
        UserEntity userEntity = userMapper.mapFrom(user);
        UserEntity savedUserEntity = userService.registerUser(userEntity);
        return userMapper.mapTo(savedUserEntity);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") UUID id, @RequestBody UserDto userDto){
        if(!userService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDto.setId(id);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.registerUser(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") UUID id){
        Optional<UserEntity> foundUser = userService.findById(id);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/loginSuccesful")
    public String logInGoogle(OAuth2AuthenticationToken oAuth2AuthenticationToken ){
        UUID id = oAuth2AuthenticationToken.getPrincipal().getAttribute("id");
        Optional<UserEntity> userEntity = userService.findById(id);
        if(userEntity.isPresent()){
            return "Benvenuto";
        } else {
            return "Utente non trovato";
        }
    }

//    @GetMapping("/oauth")
//    public UserEntity getUser(@AuthenticationPrincipal OAuth2User principal){
//        String email = principal.getAttribute("email");
//        String name = principal.getAttribute("name");
//        return userService.registerOrLoginUserOAuth2(email, name);
//    }


    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
