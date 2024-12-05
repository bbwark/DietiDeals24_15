package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.RoleService;
import com.dietideals.dietideals24_25.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private Mapper<UserEntity, UserDto> userMapper;
    PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, Mapper<UserEntity, UserDto> userMapper,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('BUYER') && #id == #userDto.getId().toString() && @userSecurityService.isUserAuthorized(#id)")
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        try {
            UUID idConverted = UUID.fromString(id);
            
            if (!userService.exists(idConverted)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            userDto.setId(idConverted);
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            } else {
                UserEntity existingUser = userService.findById(idConverted).get();
                userDto.setPassword(existingUser.getPassword());
            }

            if (userDto.getIsSeller() != null && userDto.getIsSeller() &&
                    (userDto.getAddress() == null || userDto.getAddress().isEmpty() ||
                            userDto.getZipcode() == null || userDto.getZipcode().isEmpty() ||
                            userDto.getCountry() == null || userDto.getCountry().isEmpty() ||
                            userDto.getPhoneNumber() == null || userDto.getPhoneNumber().isEmpty() ||
                            userDto.getCreditCards() == null || userDto.getCreditCards().isEmpty())) {
                userDto.setIsSeller(false);
            }

            Set<RoleEntity> authorities = new HashSet<>();
            RoleEntity buyerRole = roleService.findByAuthority("BUYER")
                    .orElseThrow(() -> new RuntimeException("BUYER role not found"));
                    
            if (userDto.getIsSeller() != null && userDto.getIsSeller()) {
                RoleEntity sellerRole = roleService.findByAuthority("SELLER")
                        .orElseThrow(() -> new RuntimeException("SELLER role not found"));
                authorities.add(sellerRole);
            }
            authorities.add(buyerRole);

            UserEntity userEntity = userMapper.mapFrom(userDto);
            userEntity.setAuthorities(authorities);
            UserEntity savedUserEntity = userService.save(userEntity);
            return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#id)")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") String id) {
        try {
            UUID idConverted = UUID.fromString(id);
            Optional<UserEntity> foundUser = userService.findById(idConverted);
            return foundUser.map(userEntity -> {
                UserDto userDto = userMapper.mapTo(userEntity);
                userDto.setPassword(null);
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @GetMapping(path = "/info/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("id") String id) {
        try {
            UUID idConverted = UUID.fromString(id);
            Optional<UserEntity> foundUser = userService.findById(idConverted);
            return foundUser.map(userEntity -> {
                UserDto userDto = userMapper.mapTo(userEntity);
                userDto = new UserDto(userDto.getId(), userDto.getName(), userDto.getIsSeller(), userDto.getBio());
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#userId)")
    @PutMapping(path = "/add/favourite/{auctionId}/{userId}")
    public ResponseEntity<Void> addFavouriteAuction(@PathVariable("auctionId") String auctionId,
            @PathVariable("userId") String userId) {
        try {
            UUID auctionIdConverted = UUID.fromString(auctionId);
            UUID userIdConverted = UUID.fromString(userId);

            userService.addFavouriteAuction(userIdConverted, auctionIdConverted);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#userId)")
    @PutMapping(path = "/remove/favourite/{auctionId}/{userId}")
    public ResponseEntity<Void> removeFavouriteAuction(@PathVariable("auctionId") String auctionId,
            @PathVariable("userId") String userId) {
        try {
            
            UUID auctionIdConverted = UUID.fromString(auctionId);
            UUID userIdConverted = UUID.fromString(userId);

            userService.removeFavouriteAuction(userIdConverted, auctionIdConverted);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#id)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        try {
            UUID idConverted = UUID.fromString(id);
            userService.delete(idConverted);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
