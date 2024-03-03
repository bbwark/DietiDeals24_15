package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.repositories.AuctionRepository;
import com.dietideals.dietideals24_25.services.AuctionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuctionServiceImpl implements AuctionService {

    private AuctionRepository auctionRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public AuctionEntity save(AuctionEntity auctionEntity) {
        return auctionRepository.save(auctionEntity);
    }

    @Override
    public Optional<AuctionEntity> findById(UUID id) {
        return auctionRepository.findById(id);
    }

    @Override
    public List<AuctionEntity> findByItemName(String itemName) {
        return auctionRepository.findByItemName(itemName);
    }

    @Override
    public List<AuctionEntity> findRandomAuctions(UUID ownerId) {
        PageRequest pageRequest = PageRequest.of(0, 4, Sort.by("uuid"));
        return auctionRepository.findRandomAuctions(ownerId, pageRequest);
    }

    @Override
    public Boolean exists(UUID id) {
        return auctionRepository.existsById(id);
    }

    @Override
    public void delete(UUID id) {
        auctionRepository.deleteById(id);
    }
}
