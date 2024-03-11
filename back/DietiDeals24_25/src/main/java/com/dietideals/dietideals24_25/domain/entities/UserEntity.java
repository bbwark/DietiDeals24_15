package com.dietideals.dietideals24_25.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    private String name;

    private String surname;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean isSeller;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private List<AuctionEntity> favouriteAuctionEntities = new ArrayList<>();

    private String bio;

    private String address;

    private String phoneNumber;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_number")
    private List<CreditCardEntity> creditCards = new ArrayList<>();

}