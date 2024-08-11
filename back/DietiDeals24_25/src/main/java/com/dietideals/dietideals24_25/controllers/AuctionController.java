package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.dto.ItemDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import com.dietideals.dietideals24_25.services.ItemService;
import com.dietideals.dietideals24_25.services.UserService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionService auctionService;
    private UserService userService;
    private ItemService itemService;
    private Mapper<AuctionEntity, AuctionDto> auctionMapper;
    private Mapper<ItemEntity, ItemDto> itemMapper;
    private Mapper<UserEntity, UserDto> userMapper;

    public AuctionController(AuctionService auctionService, UserService userService, ItemService itemService,
            Mapper<AuctionEntity, AuctionDto> auctionMapper, Mapper<ItemEntity, ItemDto> itemMapper,
            Mapper<UserEntity, UserDto> userMapper) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.itemService = itemService;
        this.auctionMapper = auctionMapper;
        this.itemMapper = itemMapper;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<AuctionDto> createAuction(@RequestBody AuctionDto auction) {
        try {
            boolean itemExists = auction.getItem() != null;
            ItemEntity item = null;
            if (itemExists) {
                item = itemMapper.mapFrom(auction.getItem());
                auction.setItem(null);
            }

            AuctionEntity auctionEntity = auctionMapper.mapFrom(auction);
            UserEntity owner = userService.findById(auction.getOwnerId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("User with id " + auction.getOwnerId() + " not found"));
            auctionEntity.setOwner(owner);
            AuctionEntity savedAuctionEntity = auctionService.save(auctionEntity);
            AuctionDto responseAuction = auctionMapper.mapTo(savedAuctionEntity);

            if (itemExists) {
                item.setAuction(savedAuctionEntity);
                item = itemService.save(item);
                responseAuction.setItem(itemMapper.mapTo(item));
            }

            return new ResponseEntity<>(responseAuction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuctionDto> updateAuction(@PathVariable("id") UUID id, @RequestBody AuctionDto auction) {
        try {
            if (!auctionService.exists(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            auction.setId(id);
            AuctionEntity auctionEntity = auctionMapper.mapFrom(auction);
            AuctionEntity savedAuctionEntity = auctionService.save(auctionEntity);
            return new ResponseEntity<>(auctionMapper.mapTo(savedAuctionEntity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuctionDto> getAuction(@PathVariable("id") UUID id) {
        try {
            Optional<AuctionEntity> foundAuction = auctionService.findById(id);
            return foundAuction.map(auctionEntity -> {
                AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);
                return new ResponseEntity<>(auctionDto, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/bidders/{id}")
    public ResponseEntity<List<UserDto>> getAuctionBidders(@PathVariable("id") UUID id) {
        try {
            List<UserEntity> bidders = auctionService.findBiddersByAuctionId(id);
            List<UserDto> result = bidders.stream()
                    .map(bidder -> userMapper.mapTo(bidder))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/item/{name}")
    public ResponseEntity<List<AuctionDto>> listAuctionsByItemName(@PathVariable("name") String itemName) {
        try {
            List<AuctionEntity> auctions = auctionService.findByItemName(itemName);
            List<AuctionDto> result = auctions.stream()
                    .map(auction -> auctionMapper.mapTo(auction))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/owner/{id}")
    public ResponseEntity<List<AuctionDto>> listRandomAuctions(@PathVariable("id") UUID ownerId) {
        try {
            List<AuctionEntity> auctions = auctionService.findRandomAuctions(ownerId);
            List<AuctionDto> result = auctions.stream()
                    .map(auction -> auctionMapper.mapTo(auction))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}