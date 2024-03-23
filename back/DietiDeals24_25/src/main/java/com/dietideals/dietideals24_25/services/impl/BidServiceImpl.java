package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.repositories.BidRepository;
import com.dietideals.dietideals24_25.services.BidService;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {
    private BidRepository bidRepository;

    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public BidEntity createBid(BidEntity bidEntity) {
        return bidRepository.save(bidEntity);
    }

    @Override
    public void delete(String id) {
        bidRepository.deleteById(id);
    }
}
