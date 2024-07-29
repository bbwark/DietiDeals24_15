package com.dietideals.dietideals24_25.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@AllArgsConstructor
@Builder
@Data
@Table(name = "users")
public class UserEntity implements UserDetails {

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
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AuctionEntity> favouriteAuctionEntities = new ArrayList<>();

    @Column(name = "bio")
    private String bio;

    @Column(name = "address")
    private String address;

    @Column(name = "zipCode")
    private Integer zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Builder.Default
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<CreditCardEntity> creditCards = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BidEntity> bids = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_junction", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> authorities = new HashSet<>();

    public UserEntity() {
        super();
        this.authorities = new HashSet<Role>();
    }

    public UserEntity(UUID id, String email, String name, String surname, String password, Set<Role> authorities,
            Boolean isSeller) {
        super();
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.authorities = authorities;
        this.isSeller = isSeller;
    }

    public UserEntity(UUID id, String email, String name, String surname, String password, Set<Role> authorities,
            Boolean isSeller, String address, Integer zipCode, String country, String phoneNumber,
            List<CreditCardEntity> creditCards) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.authorities = authorities;
        this.isSeller = isSeller;
        this.zipCode = zipCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.creditCards = creditCards;
    }

    public UserEntity(UUID id, String email, String name, Set<Role> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
