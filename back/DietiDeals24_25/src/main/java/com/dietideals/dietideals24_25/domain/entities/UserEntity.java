package com.dietideals.dietideals24_25.domain.entities;

import com.dietideals.dietideals24_25.domain.Country;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(name = "isSeller")
    private Boolean isSeller;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AuctionEntity> ownedAuctions = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_favourite_auctions", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "auction_id"))
    private Set<AuctionEntity> favouriteAuctions = new HashSet<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_device_tokens", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "device_token")
    private List<String> deviceTokens = new ArrayList<>();

    @Column(name = "bio")
    private String bio;

    @Column(name = "address")
    private String address;

    @Column(name = "zipCode")
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private List<CreditCardEntity> creditCards = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<BidEntity> bids = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_junction", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<RoleEntity> authorities = new HashSet<>();
}
