package com.dietideals.dietideals24_25.domain.entities;

import com.dietideals.dietideals24_25.domain.AuctionCategory;
import com.dietideals.dietideals24_25.domain.AuctionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "auctions")
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "auction", cascade = CascadeType.ALL)
    private ItemEntity item;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "auction", cascade = CascadeType.ALL)
    private List<BidEntity> bids = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "favouriteAuctions", fetch = FetchType.LAZY)
    private Set<UserEntity> favouritedByUsers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AuctionType type;

    @Enumerated(EnumType.STRING)
    private AuctionCategory category;

    private LocalDateTime endingDate;

    private Boolean expired;

    private String description;

    private String minStep;

    private String interval;

    private String startingPrice;

    private String buyoutPrice;
}