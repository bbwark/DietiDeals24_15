package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.Country;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

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

    @Builder.Default
    private List<AuctionDto> ownedAuctions = new ArrayList<>();

    @Builder.Default
    private List<AuctionDto> favouriteAuctions = new ArrayList<>();

    @Builder.Default
    private List<String> deviceTokens = new ArrayList<>();

    private Optional<String> bio;

    private Optional<String> address;

    private Optional<String> zipcode;

    private Optional<Country> country;

    private Optional<String> phoneNumber;

    @Builder.Default
    private List<CreditCardDto> creditCards = new ArrayList<>();

    private Set<RoleEntity> authorities;

    public UserDto(UUID id, String email, String name, Set<RoleEntity> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.authorities = authorities;
        ownedAuctions = new ArrayList<>();
        favouriteAuctions = new ArrayList<>();
        creditCards = new ArrayList<>();
    }

    public UserDto(UUID id, String name, Boolean isSeller, Optional<String> bio) {
        this.id = id;
        this.name = name;
        this.isSeller = isSeller;
        this.bio = bio;
        ownedAuctions = new ArrayList<>();
        favouriteAuctions = new ArrayList<>();
        creditCards = new ArrayList<>();
    }
}
