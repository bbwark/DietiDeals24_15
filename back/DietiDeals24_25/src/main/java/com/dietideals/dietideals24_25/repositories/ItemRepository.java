package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.ItemEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, UUID> {
    Optional<ItemEntity> findById(UUID id);
}
