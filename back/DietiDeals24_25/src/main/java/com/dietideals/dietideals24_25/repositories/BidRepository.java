package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.BidEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface BidRepository extends CrudRepository<BidEntity, UUID> {

    @Query("SELECT b FROM BidEntity b WHERE b.auction.id = :auctionId ORDER BY b.value DESC")
    List<BidEntity> findByAuctionId(@Param("auctionId") UUID auctionId);
}