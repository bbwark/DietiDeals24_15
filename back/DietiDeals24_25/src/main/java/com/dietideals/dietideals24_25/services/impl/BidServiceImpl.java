package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.repositories.BidRepository;
import com.dietideals.dietideals24_25.services.BidService;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {
    private BidRepository bidRepository;

    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public BidEntity save(BidEntity bidEntity) {
        return bidRepository.save(bidEntity);
    }

    @Override
    public void delete(UUID id) {
        bidRepository.deleteById(id);
    }

    @Override
    public List<BidEntity> findByAuctionId(UUID auctionId) {
        return bidRepository.findByAuctionId(auctionId);
    }
}
