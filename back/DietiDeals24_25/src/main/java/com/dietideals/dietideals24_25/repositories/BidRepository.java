package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.BidEntity;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends CrudRepository<BidEntity, UUID> {
}