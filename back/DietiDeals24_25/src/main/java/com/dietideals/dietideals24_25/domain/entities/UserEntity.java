package com.dietideals.dietideals24_25.domain.entities;

import com.dietideals.dietideals24_25.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean isSeller;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private ArrayList<AuctionEntity> favouriteAuctionEntities = new ArrayList<>();

    private String bio;

    private String address;

    private String phoneNumber;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_number")
    private ArrayList<CreditCardEntity> creditCards = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    public UserEntity(Integer id, String email, String password, Set<Role> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

}