package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.BidService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bids")
public class BidController {

    private BidService bidService;

    private Mapper<BidEntity, BidDto> bidMapper;

    public BidController(BidService bidService, Mapper<BidEntity, BidDto> bidMapper) {
        this.bidService = bidService;
        this.bidMapper = bidMapper;
    }

    @PostMapping
    public ResponseEntity<BidDto> createBid(@RequestBody BidDto bid) {
        try {
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
