package com.dietideals.dietideals24_25.utils;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.UserService;

@Service
public class UserLoaderHelper {
    private UserService userService;
    private Mapper<UserEntity, UserDto> userMapper;

    public UserLoaderHelper(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDto userLoader(String id) {
        Optional<UserEntity> userEntityOpt = userService.findById(UUID.fromString(id));
        UserDto userDto = null;
        if (userEntityOpt.isPresent()) {
            UserEntity userEntity = userEntityOpt.get();
            userDto = userMapper.mapTo(userEntity);
        }
        return userDto;
    }
}