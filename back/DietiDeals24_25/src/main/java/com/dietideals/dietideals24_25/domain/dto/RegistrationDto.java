package com.dietideals.dietideals24_25.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDto {

    private String email;
    private String password;
}
