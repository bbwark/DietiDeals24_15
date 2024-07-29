package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.entities.Role;
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
    private List<AuctionDto> favouriteAuctionEntities = new ArrayList<>();

    private Optional<String> bio;

    private Optional<String> address;

    private Optional<Integer> zipcode;

    private Optional<String> country;

    private Optional<String> phoneNumber;

    @Builder.Default
    private List<CreditCardDto> creditCards = new ArrayList<>();

    private Set<Role> authorities;

    public UserDto(UUID id, String email, String name, Set<Role> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.authorities = authorities;
    }
}
