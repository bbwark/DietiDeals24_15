package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import lombok.Data;

@Data
public class LoginDto {

    private UserEntity userEntity;
    private String jwt;

    public LoginDto(){
        super();
    }

    public LoginDto(UserEntity userEntity, String jwt){
        this.userEntity = userEntity;
        this.jwt = jwt;
    }
}
