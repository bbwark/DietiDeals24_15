package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.AuctionType;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final int MAX_PAGE_SIZE = 5;

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

    @PreAuthorize("hasAuthority('SELLER')")
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

            if (itemExists && item != null) {
                item.setAuction(savedAuctionEntity);
                item = itemService.save(item);
                responseAuction.setItem(itemMapper.mapTo(item));
            }

            return new ResponseEntity<>(responseAuction, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('SELLER') && #id == #auction.getId().toString() && @userSecurityService.isUserAuthorized(#auction.getOwnerId().toString())")
    @PutMapping(path = "/{id}")
    public ResponseEntity<AuctionDto> updateAuction(@PathVariable("id") String id, @RequestBody AuctionDto auction) {
        try {
            UUID idConverted = UUID.fromString(id);
            if (!auctionService.exists(idConverted)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            auction.setId(idConverted);
            AuctionEntity auctionEntity = auctionMapper.mapFrom(auction);
            AuctionEntity savedAuctionEntity = auctionService.save(auctionEntity);
            return new ResponseEntity<>(auctionMapper.mapTo(savedAuctionEntity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<AuctionDto> getAuction(@PathVariable("id") String id) {
        try {
            UUID idConverted = UUID.fromString(id);
            Optional<AuctionEntity> foundAuction = auctionService.findById(idConverted);
            return foundAuction.map(auctionEntity -> {
                AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);
                if (auctionDto.getType() == AuctionType.Silent) {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null && auth.getPrincipal() instanceof UserDto) {
                        UserDto user = (UserDto) auth.getPrincipal();
                        if (!user.getId().equals(auctionDto.getOwnerId())) {
                            auctionDto.getBids().clear(); //if the user who's making the request is not the owner, he can't see the bids
                        }
                    }
                }
                return new ResponseEntity<>(auctionDto, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('SELLER') && @userSecurityService.isUserAuthorizedByAuctionId(#id)")
    @GetMapping(path = "/bidders/{id}")
    public ResponseEntity<List<UserDto>> getAuctionBidders(@PathVariable("id") String id) {
        try {
            UUID idConverted = UUID.fromString(id);
            List<UserEntity> bidders = auctionService.findBiddersByAuctionId(idConverted);
            List<UserDto> result = bidders.stream()
                    .map(bidder -> userMapper.mapTo(bidder))
                    .collect(Collectors.toList());
            result = result.stream()
                    .map(userDto -> new UserDto(userDto.getId(), userDto.getName(), userDto.getIsSeller(),
                            userDto.getBio()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER')")
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

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#id)")
    @GetMapping(path = "/owner/{id}")
    public ResponseEntity<List<AuctionDto>> listRandomAuctions(@PathVariable("id") String id) {
        try {
            UUID ownerId = UUID.fromString(id);
            List<AuctionEntity> auctions = auctionService.findRandomAuctions(ownerId, MAX_PAGE_SIZE);
            List<AuctionDto> result = auctions.stream()
                    .map(auction -> auctionMapper.mapTo(auction))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#id)")
    @GetMapping(path = "/participated/{id}")
    public ResponseEntity<List<AuctionDto>> listParticipatedAuctions(@PathVariable("id") String id) {
        try {
            UUID ownerId = UUID.fromString(id);
            List<AuctionEntity> auctions = auctionService.findParticipatedAuctions(ownerId, MAX_PAGE_SIZE);
            List<AuctionDto> result = auctions.stream()
                    .map(auction -> auctionMapper.mapTo(auction))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#id)")
    @GetMapping(path = "/ending/{id}")
    public ResponseEntity<List<AuctionDto>> listEndingAuctions(@PathVariable("id") String id) {
        try {
            UUID ownerId = UUID.fromString(id);
            List<AuctionEntity> auctions = auctionService.findEndingAuctions(ownerId, MAX_PAGE_SIZE);
            List<AuctionDto> result = auctions.stream()
                    .map(auction -> auctionMapper.mapTo(auction))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}