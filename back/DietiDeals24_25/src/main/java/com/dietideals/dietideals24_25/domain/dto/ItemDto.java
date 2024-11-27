package com.dietideals.dietideals24_25.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private UUID id;

    private String name;

    @Builder.Default
    private ArrayList<String> imageUrl = new ArrayList<>();

    private UUID auctionId;
}
