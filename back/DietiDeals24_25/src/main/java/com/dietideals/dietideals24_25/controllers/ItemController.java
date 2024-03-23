package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.ItemDto;
import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {

    private ItemService itemService;

    private Mapper<ItemEntity, ItemDto> itemMapper;

    public ItemController(ItemService itemService, Mapper<ItemEntity, ItemDto> itemMapper){
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @PostMapping(path = "/items")
    public ItemDto createItem(@RequestBody ItemDto item){
        ItemEntity itemEntity = itemMapper.mapFrom(item);
        ItemEntity savedItemEntity = itemService.createItem(itemEntity);
        return itemMapper.mapTo(savedItemEntity);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") String id){
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
