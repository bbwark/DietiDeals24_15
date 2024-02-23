package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    ItemEntity createItem(ItemEntity itemEntity);
    void delete (String id);
}
