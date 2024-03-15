package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    private List<AuctionEntity> favouriteAuctionEntities = new ArrayList<>();

    private Optional<String> bio;

    private Optional<String> address;

    private Optional<Integer> zipcode;

    private Optional<String> country;

    private Optional<String> phoneNumber;

    private List<CreditCardEntity> creditCards = new ArrayList<>();
}
