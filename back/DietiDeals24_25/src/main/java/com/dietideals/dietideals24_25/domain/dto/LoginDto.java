package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.entities.ApplicationUser;
import lombok.Data;

@Data
public class LoginDto {

    private ApplicationUser user;
    private String jwt;

    public LoginDto(ApplicationUser user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

}
