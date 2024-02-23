package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.repositories.ItemRepository;
import com.dietideals.dietideals24_25.services.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemEntity createItem(ItemEntity itemEntity) {
        return itemRepository.save(itemEntity);
    }

    @Override
    public void delete(String id) {
        itemRepository.deleteById(id);
    }
}
