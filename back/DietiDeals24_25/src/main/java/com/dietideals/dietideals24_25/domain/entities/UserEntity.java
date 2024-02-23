package com.dietideals.dietideals24_25.domain.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private String password;

    private Boolean isSeller;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private ArrayList<AuctionEntity> favouriteAuctionEntities = new ArrayList<AuctionEntity>();

    private Optional<String> bio;

    private Optional<String> address;

    private Optional<String> phoneNumber;

    /*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "number")
    private ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();*/
}