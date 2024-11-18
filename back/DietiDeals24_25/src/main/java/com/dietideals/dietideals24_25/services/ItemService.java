package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.ItemEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    ItemEntity save(ItemEntity itemEntity);

    void delete(UUID id);

    Optional<ItemEntity> findById(UUID id);
}
