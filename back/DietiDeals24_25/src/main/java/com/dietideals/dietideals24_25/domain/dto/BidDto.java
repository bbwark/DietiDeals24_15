package com.dietideals.dietideals24_25.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidDto {

    private UUID id;

    private Float value;

    private String date;

    private UUID userId;

    private UUID auctionId;
}
