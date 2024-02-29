package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuctionRepository extends CrudRepository<AuctionEntity, UUID> {
    @Query("SELECT a FROM auctions a WHERE lower(a.item.name) LIKE lower(concat('%', :itemName,'%'))") //TODO: check if works
    List<AuctionEntity> findByItemName(@Param("itemName") String itemName);

    @Query("SELECT a FROM Auction a WHERE a.owner.uuid != :ownerUuid")
    List<AuctionEntity> findRandomAuctions(@Param("ownerUuid") UUID ownerUuid, Pageable pageable);

}
