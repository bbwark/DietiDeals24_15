package com.dietideals.dietideals24_25.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AuctionType {
    English,
    Silent,
    None
}
