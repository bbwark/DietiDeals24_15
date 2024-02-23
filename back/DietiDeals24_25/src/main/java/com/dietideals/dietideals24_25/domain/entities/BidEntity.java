package com.dietideals.dietideals24_25.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BidEntity {

    @Id
    private String id;

    private Float value;

    private String userId;

    private String date;


}
