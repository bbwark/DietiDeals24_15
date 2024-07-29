package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.dto.AuctionNameDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AuctionController {

    private AuctionService auctionService;

    private Mapper<AuctionEntity, AuctionDto> auctionMapper;

    public AuctionController(AuctionService auctionService, Mapper<AuctionEntity, AuctionDto> auctionMapper) {
        this.auctionService = auctionService;
        this.auctionMapper = auctionMapper;
    }

    @PostMapping(path = "/auctions")
    public AuctionDto createAuction(@RequestBody AuctionDto auction) {
        AuctionEntity auctionEntity = auctionMapper.mapFrom(auction);
        AuctionEntity savedAuctionEntity = auctionService.save(auctionEntity);
        return auctionMapper.mapTo(savedAuctionEntity);
    }

    @PutMapping(path = "/auctions/{id}")
    public ResponseEntity<AuctionDto> updateAuction(@PathVariable("id") UUID id, @RequestBody AuctionDto auction) {
        if (!auctionService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        auction.setId(id);
        AuctionEntity auctionEntity = auctionMapper.mapFrom(auction);
        AuctionEntity savedAuctionEntity = auctionService.save(auctionEntity);
        return new ResponseEntity<>(auctionMapper.mapTo(savedAuctionEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/auctions/{id}")
    public ResponseEntity<AuctionDto> getAuction(@PathVariable("id") UUID id) {
        Optional<AuctionEntity> foundAuction = auctionService.findById(id);
        return foundAuction.map(auctionEntity -> {
            AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);
            return new ResponseEntity<>(auctionDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/auctions/item/{name}")
    public List<AuctionNameDto> listAuctionsByItemName(@PathVariable("name") String itemName) {
        List<AuctionEntity> auctions = auctionService.findByItemName(itemName);
        return auctions.stream()
                .map(auction -> new AuctionNameDto(auction.getId(), auction.getItem().getName()))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/auctions/owner/{id}")
    public List<AuctionDto> listRandomAuctions(@PathVariable("id") UUID ownerId) {
        List<AuctionEntity> auctions = auctionService.findRandomAuctions(ownerId);
        return auctions.stream()
                .map(auctionMapper::mapTo)
                .collect(Collectors.toList());
    }
}