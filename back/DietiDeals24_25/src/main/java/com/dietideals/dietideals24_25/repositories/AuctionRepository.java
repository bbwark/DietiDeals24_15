package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuctionRepository extends CrudRepository<AuctionEntity, UUID> {
    @Query("SELECT a FROM AuctionEntity a WHERE lower(a.item.name) LIKE lower(concat('%', :itemName,'%'))")
    List<AuctionEntity> findByItemName(@Param("itemName") String itemName);

    @Query("SELECT a FROM AuctionEntity a WHERE a.owner.id != :ownerUuid")
    List<AuctionEntity> findRandomAuctions(@Param("ownerUuid") UUID ownerUuid, Pageable pageable);

    @Query("SELECT DISTINCT u FROM BidEntity b JOIN b.user u WHERE b.auction.id = :auctionId")
    List<UserEntity> findBiddersByAuctionId(@Param("auctionId") UUID auctionId);

    @Query("SELECT a FROM AuctionEntity a WHERE a.endingDate < CURRENT_TIMESTAMP AND a.expired = false")
    List<AuctionEntity> findExpiredAuctions();
}