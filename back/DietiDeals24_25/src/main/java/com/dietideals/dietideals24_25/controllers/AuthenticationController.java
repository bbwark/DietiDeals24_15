package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.dto.LoginDto;
import com.dietideals.dietideals24_25.domain.dto.LoginRequest;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.CreditCardService;
import com.dietideals.dietideals24_25.services.RoleService;
import com.dietideals.dietideals24_25.services.UserService;
import com.dietideals.dietideals24_25.services.impl.AuthenticationServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;
    private CreditCardService creditCardService;
    private Mapper<CreditCardEntity, CreditCardDto> creditCardMapper;
    private Mapper<UserEntity, UserDto> userMapper;
    private AuthenticationServiceImpl authenticationService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, CreditCardService creditCardService,
            Mapper<CreditCardEntity, CreditCardDto> creditCardMapper,
            Mapper<UserEntity, UserDto> userMapper, AuthenticationServiceImpl authenticationService,
            RoleService roleService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.creditCardService = creditCardService;
        this.creditCardMapper = creditCardMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user) {
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
            RoleEntity buyerRole = roleService.findByAuthority("BUYER")
                    .orElseThrow(() -> new RuntimeException("BUYER role not found"));
            if (user.getIsSeller()) {
                RoleEntity sellerRole = roleService.findByAuthority("SELLER")
                        .orElseThrow(() -> new RuntimeException("SELLER role not found"));
                authorities.add(sellerRole);
            }
            authorities.add(buyerRole);

            // Map to UserEntity and set proper authorities
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserEntity userEntity = userMapper.mapFrom(user);
            userEntity.setAuthorities(authorities);

            // Save user and handle credit cards
            UserEntity savedUserEntity = userService.save(userEntity);
            UserDto responseUser = userMapper.mapTo(savedUserEntity);

            if (userHasCreditCards && creditCardDtos != null) {
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

    @PostMapping("/loginUser")
    public ResponseEntity<LoginDto> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginDto login = authenticationService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            return new ResponseEntity<>(login, login.getJwt().isEmpty() ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginDto(null, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
