package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    private String name;

    private String email;

    private String password;

    private Boolean isSeller;

    private ArrayList<AuctionEntity> favouriteAuctionEntities = new ArrayList<>();

    private Optional<String> bio;

    private Optional<String> address;

    private Optional<String> phoneNumber;

    private ArrayList<CreditCardEntity> creditCards = new ArrayList<>();
}
