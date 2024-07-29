package com.dietideals.dietideals24_25.domain.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDto {

    private String creditCardNumber;

    private String expirationDate;

    private Integer cvv;

    private String iban;

    private UUID ownerId;

    public CreditCardDto(CreditCardDto other) {
        this.creditCardNumber = other.creditCardNumber;
        this.expirationDate = other.expirationDate;
        this.cvv = other.cvv;
        this.iban = other.iban;
        this.ownerId = other.ownerId;
    }
}
