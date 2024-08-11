package com.dietideals.dietideals24_25.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    private Optional<UserDto> user;
    private String jwt;

}
