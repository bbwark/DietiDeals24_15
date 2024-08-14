package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.CreditCardService;
import com.dietideals.dietideals24_25.services.RoleService;
import com.dietideals.dietideals24_25.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private CreditCardService creditCardService;
    private RoleService roleService;
    private Mapper<UserEntity, UserDto> userMapper;
    private Mapper<CreditCardEntity, CreditCardDto> creditCardMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController(UserService userService, CreditCardService creditCardService, RoleService roleService,
            Mapper<UserEntity, UserDto> userMapper, Mapper<CreditCardEntity, CreditCardDto> creditCardMapper,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.creditCardMapper = creditCardMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/google")
    public ResponseEntity<?> getOAuthUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return ResponseEntity.ok(oAuth2User.getAttributes());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        try {
            boolean userHasCreditCards = user.getCreditCards() != null && !user.getCreditCards().isEmpty();
            List<CreditCardDto> creditCardDtos = null;
            if (userHasCreditCards) {
                creditCardDtos = user.getCreditCards().stream()
                        .map(creditCard -> {
                            CreditCardDto creditCardDto = new CreditCardDto(creditCard);
                            return creditCardDto;
                        })
                        .collect(Collectors.toList());

                user.getCreditCards().clear();
            }

            if (user.getAddress() == null || user.getAddress().isEmpty() ||
                    user.getZipcode() == null || user.getZipcode().isEmpty() ||
                    user.getCountry() == null || user.getCountry().isEmpty() ||
                    user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty() ||
                    !userHasCreditCards) {
                user.setIsSeller(false);
            }

            Set<RoleEntity> authorities = new HashSet<>();
            authorities.add(roleService.findByAuthority("USER").get());
            user.setAuthorities(authorities);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            UserEntity userEntity = userMapper.mapFrom(user);
            UserEntity savedUserEntity = userService.save(userEntity);
            UserDto responseUser = userMapper.mapTo(savedUserEntity);

            if (userHasCreditCards) {
                creditCardDtos.forEach(creditCardDto -> {
                    creditCardDto.setOwnerId(savedUserEntity.getId());
                    creditCardService.save(creditCardMapper.mapFrom(creditCardDto));
                    responseUser.getCreditCards().add(creditCardDto);
                });
            }

            return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") UUID id, @RequestBody UserDto userDto) {
        try {
            if (!userService.exists(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userDto.setId(id);
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
