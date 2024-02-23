package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.AuctionType;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionDto {

    private UUID id;

    private UserEntity owner;

    /*
    private Item item;*/

    /*
    private Array<Bid> bids = new ArrayList<Bid>();*/

    private Optional<LocalDate> endingDate;

    private Boolean expired;

    private AuctionType auctionType;
}
