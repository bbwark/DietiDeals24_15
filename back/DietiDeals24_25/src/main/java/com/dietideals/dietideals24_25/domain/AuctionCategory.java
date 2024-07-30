package com.dietideals.dietideals24_25.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AuctionCategory {
    Electronic,
    Games,
    House,
    Engine,
    Book,
    Fashion,
    Sport,
    Music,
    Other
}
