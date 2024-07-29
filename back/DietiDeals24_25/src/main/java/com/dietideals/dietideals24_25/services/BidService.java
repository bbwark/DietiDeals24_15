package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import org.springframework.stereotype.Service;

@Service
public interface BidService {
    BidEntity createBid(BidEntity bidEntity);

    void delete(String id);

}
