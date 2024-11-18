package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.BidEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface BidService {
    BidEntity save(BidEntity bidEntity);

    void delete(UUID id);

    public List<BidEntity> findByAuctionId(UUID auctionId);

    Optional<BidEntity> findById(UUID id);
}
