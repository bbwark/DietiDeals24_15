package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.BidService;
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

    @PostMapping(path = "/")
    public ResponseEntity<BidDto> createBid(@RequestBody BidDto bid) {
        BidEntity bidEntity = bidMapper.mapFrom(bid);
        BidEntity savedBidEntity = bidService.createBid(bidEntity);
        BidDto responseBid = bidMapper.mapTo(savedBidEntity);
        return new ResponseEntity<>(responseBid, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBid(@PathVariable("id") String id) {
        bidService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
