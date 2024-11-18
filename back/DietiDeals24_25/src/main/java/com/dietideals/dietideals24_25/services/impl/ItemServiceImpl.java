package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.repositories.ItemRepository;
import com.dietideals.dietideals24_25.services.ItemService;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemEntity save(ItemEntity itemEntity) {
        return itemRepository.save(itemEntity);
    }

    @Override
    public void delete(UUID id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Optional<ItemEntity> findById(UUID id) {
        return itemRepository.findById(id);
    }
}
