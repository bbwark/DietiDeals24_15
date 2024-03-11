package com.dietideals.dietideals24_25.domain.dto;

import lombok.Data;

@Data
public class RegistrationDto {

    private String username;

    private String password;

    public RegistrationDto(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }

    public String toString(){
        return "Registration info: username: "+ this.username + " password: "+ this.password;
    }

}
