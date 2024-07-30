package com.dietideals.dietideals24_25.domain.entities;

import com.dietideals.dietideals24_25.domain.AuctionCategory;
import com.dietideals.dietideals24_25.domain.AuctionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    private ItemEntity item;

    @Builder.Default
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<BidEntity> bids = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AuctionType type;

    @Enumerated(EnumType.STRING)
    private AuctionCategory category;

    private LocalDate endingDate;

    private Boolean expired;

    private String description;

    private String minStep;

    private String interval;
    
    private String startingPrice;
}