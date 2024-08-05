package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.AuctionType;
import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import com.dietideals.dietideals24_25.services.BidService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/bids")
public class BidController {

    private BidService bidService;
    private AuctionService auctionService;
    private Mapper<BidEntity, BidDto> bidMapper;
    private Mapper<AuctionEntity, AuctionDto> auctionMapper;

    public BidController(BidService bidService, AuctionService auctionService, Mapper<BidEntity, BidDto> bidMapper,
            Mapper<AuctionEntity, AuctionDto> auctionMapper) {
        this.bidService = bidService;
        this.auctionService = auctionService;
        this.bidMapper = bidMapper;
        this.auctionMapper = auctionMapper;
    }

    @PostMapping
    public ResponseEntity<BidDto> createBid(@RequestBody BidDto bid) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss:SSS"));
            bid.setDate(timestamp);

            AuctionEntity auctionEntity = auctionService.findById(bid.getAuctionId())
                    .orElseThrow(() -> new RuntimeException("Auction with id " + bid.getAuctionId() + " not found"));
            AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);
            
            if (auctionDto.getOwnerId().equals(bid.getUserId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            if (auctionDto.getType() == AuctionType.English) {
                List<BidEntity> bidEntities = bidService.findByAuctionId(auctionDto.getId());
                List<BidDto> bidDtos = bidEntities.stream()
                        .map(bidEntity -> bidMapper.mapTo(bidEntity))
                        .collect(java.util.stream.Collectors.toList());
                
                for (BidDto bidDto : bidDtos) {
                    if (bidDto.getValue() >= bid.getValue()) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }
                }
            }

            BidEntity bidEntity = bidMapper.mapFrom(bid);
            BidEntity savedBidEntity = bidService.save(bidEntity);
            BidDto responseBid = bidMapper.mapTo(savedBidEntity);
            return new ResponseEntity<>(responseBid, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBid(@PathVariable("id") UUID id) {
        try {
            bidService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/auction/{auctionId}")
    public ResponseEntity<List<BidDto>> getBidsByAuctionId(@PathVariable("auctionId") UUID auctionId) {
        try {
            List<BidEntity> bidEntities = bidService.findByAuctionId(auctionId);
            List<BidDto> result = bidEntities.stream()
                    .map(bidEntity -> bidMapper.mapTo(bidEntity))
                    .collect(java.util.stream.Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
