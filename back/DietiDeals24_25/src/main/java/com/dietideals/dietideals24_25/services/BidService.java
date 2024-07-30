package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.BidEntity;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface BidService {
    BidEntity save(BidEntity bidEntity);

    void delete(UUID id);

}
