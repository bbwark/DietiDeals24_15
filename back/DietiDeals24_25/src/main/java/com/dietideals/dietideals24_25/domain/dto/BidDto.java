package com.dietideals.dietideals24_25.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidDto {

    private String id;

    private Float value;

    private String userId;

    private String date;


}
