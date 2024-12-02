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
    @Query("SELECT a FROM AuctionEntity a WHERE lower(a.item.name) LIKE lower(concat('%', :itemName,'%')) ORDER BY a.item.name ASC")
    List<AuctionEntity> findByItemName(@Param("itemName") String itemName, Pageable pageable);

    @Query("SELECT a FROM AuctionEntity a WHERE a.owner.id != :ownerUuid AND a.expired = false ORDER BY RANDOM()")
    List<AuctionEntity> findRandomAuctions(@Param("ownerUuid") UUID ownerUuid, Pageable pageable);

    @Query("SELECT DISTINCT u FROM BidEntity b JOIN b.user u WHERE b.auction.id = :auctionId")
    List<UserEntity> findBiddersByAuctionId(@Param("auctionId") UUID auctionId);

    @Query("SELECT a FROM AuctionEntity a WHERE a.endingDate < CURRENT_TIMESTAMP AND a.expired = false")
    List<AuctionEntity> findExpiredAuctions();

    @Query("SELECT DISTINCT a FROM AuctionEntity a INNER JOIN a.bids b WHERE b.user.id = :id ")
    List<AuctionEntity> findParticipatedAuctions(@Param("id") UUID userId, Pageable pageable);

    @Query("SELECT a FROM AuctionEntity a WHERE a.endingDate < DATEADD(MINUTE, -30, CURRENT_TIMESTAMP) AND a.owner.id != :id ORDER BY a.endingDate DESC")
    List<AuctionEntity> findEndingAuctions(@Param("id") UUID id, Pageable pageable);
}