package com.dietideals.dietideals24_25.domain.dto;

import com.dietideals.dietideals24_25.domain.AuctionCategory;
import com.dietideals.dietideals24_25.domain.AuctionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionDto {

    private UUID id;

    private UUID ownerId;

    private ItemDto item;

    @Builder.Default
    private ArrayList<BidDto> bids = new ArrayList<>();

    private LocalDateTime endingDate;

    private Boolean expired;

    private AuctionType type;

    private String description;

    private String minStep;

    private String interval;
    
    private String startingPrice;

    private String buyoutPrice;

    private AuctionCategory category;

    public AuctionDto(UUID id, ItemDto itemDto, Boolean expired) {
        this.id = id;
        this.item = itemDto;
        this.expired = expired;
    }

    public AuctionDto(UUID id, ItemDto itemDto, Boolean expired, AuctionCategory category) {
        this.id = id;
        this.item = itemDto;
        this.expired = expired;
        this.category = category;
    }
}
