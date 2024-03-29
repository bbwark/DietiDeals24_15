package com.dietideals.dietideals24_25.domain.entities;

import com.dietideals.dietideals24_25.domain.AuctionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity owner;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private ItemEntity item;


    @OneToMany(cascade = CascadeType.ALL)
    private ArrayList<BidEntity> bids = new ArrayList<>();

    private LocalDate endingDate;

    private Boolean expired;

    private AuctionType auctionType;
}
