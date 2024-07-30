package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.ItemDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import com.dietideals.dietideals24_25.services.ItemService;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;
    private AuctionService auctionService;
    private Mapper<ItemEntity, ItemDto> itemMapper;

    public ItemController(ItemService itemService, AuctionService auctionService,
            Mapper<ItemEntity, ItemDto> itemMapper) {
        this.itemService = itemService;
        this.auctionService = auctionService;
        this.itemMapper = itemMapper;
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto item) {
        try {
            ItemEntity itemEntity = itemMapper.mapFrom(item);
            AuctionEntity auctionEntity = auctionService.findById(item.getAuctionId())
                    .orElseThrow(() -> new RuntimeException("Auction with id " + item.getAuctionId() + " not found"));
            itemEntity.setAuction(auctionEntity);
            ItemEntity savedItemEntity = itemService.save(itemEntity);
            ItemDto responseItem = itemMapper.mapTo(savedItemEntity);
            return new ResponseEntity<>(responseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") UUID id) {
        try {
            itemService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
