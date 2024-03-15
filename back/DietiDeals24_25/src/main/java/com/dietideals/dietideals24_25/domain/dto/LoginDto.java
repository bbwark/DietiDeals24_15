package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import lombok.Data;

import java.util.Optional;

@Data
public class LoginDto {

    private Optional<UserEntity> user;
    private String jwt;

    public LoginDto(Optional<UserEntity> user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

}
