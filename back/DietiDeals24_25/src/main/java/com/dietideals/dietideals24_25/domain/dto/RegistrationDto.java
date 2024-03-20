package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegistrationDto {

    private String email;

    private String name;

    private String surname;

    private String password;

    private String bio;

    private String address;

    private Integer zipCode;

    private String country;

    private String phoneNumber;

    private List<CreditCardEntity> creditCards;

}
