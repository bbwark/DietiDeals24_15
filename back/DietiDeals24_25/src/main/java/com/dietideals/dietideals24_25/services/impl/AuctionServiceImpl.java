package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.repositories.AuctionRepository;
import com.dietideals.dietideals24_25.repositories.CustomAuctionRepository;
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
    private CustomAuctionRepository customAuctionRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository, CustomAuctionRepository customAuctionRepository) {
        this.auctionRepository = auctionRepository;
        this.customAuctionRepository = customAuctionRepository;
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
    public List<AuctionEntity> findByItemNameAndCategories(String itemName, List<String> categories, int numberOfAuctionsPerPage, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, numberOfAuctionsPerPage, Sort.by("id"));
        return customAuctionRepository.findByItemNameAndCategories(itemName, categories, pageRequest);
    }

    @Override
    public List<AuctionEntity> findRandomAuctions(UUID ownerId, int maxNumberOfAuctions) {
        PageRequest pageRequest = PageRequest.of(0, maxNumberOfAuctions, Sort.by("id"));
        return auctionRepository.findRandomAuctions(ownerId, pageRequest);
    }

    @Override
    public List<UserEntity> findBiddersByAuctionId(UUID auctionId) {
        return auctionRepository.findBiddersByAuctionId(auctionId);
    }

    @Override
    public List<AuctionEntity> findExpiredAuctions() {
        return auctionRepository.findExpiredAuctions();
    }

    @Override
    public boolean exists(UUID id) {
        return auctionRepository.existsById(id);
    }

    @Override
    public void delete(UUID id) {
        auctionRepository.deleteById(id);
    }

    @Override
    public List<AuctionEntity> findParticipatedAuctions(UUID ownerId, int maxNumberOfAuctions) {
        PageRequest pageRequest = PageRequest.of(0, maxNumberOfAuctions, Sort.by("id"));
        return auctionRepository.findParticipatedAuctions(ownerId, pageRequest);
    }

    @Override
    public List<AuctionEntity> findEndingAuctions(UUID ownerId, int maxNumberOfAuctions) {
        PageRequest pageRequest = PageRequest.of(0, maxNumberOfAuctions, Sort.by("id"));
        return auctionRepository.findEndingAuctions(ownerId, pageRequest);
    }
}
