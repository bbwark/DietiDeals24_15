package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface AuctionService {
    AuctionEntity save(AuctionEntity auctionEntity);
    Optional<AuctionEntity> findById(UUID id);
    List<AuctionEntity> findByItemName(String itemName);
    List<AuctionEntity> findRandomAuctions(UUID ownerId);
    Boolean exists(UUID id);
    void delete(UUID id);
}
