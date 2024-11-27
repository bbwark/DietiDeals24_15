package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.AuctionType;
import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import com.dietideals.dietideals24_25.services.BidService;
import com.dietideals.dietideals24_25.services.UserService;
import com.dietideals.dietideals24_25.utils.SNSService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/bids")
public class BidController {

    private SNSService snsService;
    private BidService bidService;
    private AuctionService auctionService;
    private UserService userService;
    private Mapper<BidEntity, BidDto> bidMapper;
    private Mapper<AuctionEntity, AuctionDto> auctionMapper;
    private Mapper<UserEntity, UserDto> userMapper;

    private static final String NOTFOUND = " not found";

    public BidController(SNSService snsService, BidService bidService, AuctionService auctionService,
            UserService userService,
            Mapper<BidEntity, BidDto> bidMapper,
            Mapper<AuctionEntity, AuctionDto> auctionMapper, Mapper<UserEntity, UserDto> userMapper) {
        this.snsService = snsService;
        this.bidService = bidService;
        this.auctionService = auctionService;
        this.userService = userService;
        this.bidMapper = bidMapper;
        this.auctionMapper = auctionMapper;
        this.userMapper = userMapper;
    }

    @PreAuthorize("hasAuthority('BUYER')")
    @PostMapping
    public ResponseEntity<BidDto> createBid(@RequestBody BidDto bid) {
        try {
            bid.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss:SSS")));

            AuctionEntity auctionEntity = auctionService.findById(bid.getAuctionId())
                    .orElseThrow(() -> new RuntimeException("Auction with id " + bid.getAuctionId() + NOTFOUND));
            AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);

            if (isAuctionInvalid(auctionDto, bid.getUserId().toString())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            if (auctionDto.getType() == AuctionType.English) {
                if (!isEnglishBidValid(bid, auctionDto)) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                extendAuctionEndDate(auctionEntity, auctionDto.getInterval());
            }

            boolean buyoutPriceReached = handleSilentAuction(bid, auctionEntity, auctionDto);

            BidDto responseBid = saveBidAndUpdateAuction(bid, auctionEntity, auctionDto, buyoutPriceReached);

            return new ResponseEntity<>(responseBid, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isAuctionInvalid(AuctionDto auctionDto, String userId) {
        return auctionDto.getEndingDate().isBefore(LocalDateTime.now()) ||
                auctionDto.getExpired() ||
                auctionDto.getOwnerId().toString().equals(userId);
    }

    private boolean isEnglishBidValid(BidDto bid, AuctionDto auctionDto) {
        List<BidDto> bidDtos = bidService.findByAuctionId(auctionDto.getId()).stream()
                .map(bidMapper::mapTo)
                .collect(Collectors.toList());

        float minStep = Optional.ofNullable(auctionDto.getMinStep())
                .filter(step -> !step.isEmpty())
                .map(Float::parseFloat)
                .orElse(0f);

        return bidDtos.stream().noneMatch(existingBid -> bid.getValue() < existingBid.getValue() + minStep);
    }

    private void extendAuctionEndDate(AuctionEntity auctionEntity, String interval) {
        auctionEntity.setEndingDate(LocalDateTime.now().plus(Long.parseLong(interval), ChronoUnit.HOURS));
    }

    private boolean handleSilentAuction(BidDto bid, AuctionEntity auctionEntity, AuctionDto auctionDto) {
        Float buyoutPrice = Optional.ofNullable(auctionDto.getBuyoutPrice())
                .filter(price -> !price.isEmpty())
                .map(Float::parseFloat)
                .orElse(null);

        if (buyoutPrice != null && bid.getValue() >= buyoutPrice) {
            auctionEntity.setEndingDate(LocalDateTime.now().minus(1, ChronoUnit.HOURS));
            return true;
        }
        return false;
    }

    private BidDto saveBidAndUpdateAuction(BidDto bid, AuctionEntity auctionEntity, AuctionDto auctionDto,
            boolean buyoutPriceReached) {
        BidEntity bidEntity = bidMapper.mapFrom(bid);
        BidEntity savedBidEntity = bidService.save(bidEntity);

        if (auctionDto.getType() == AuctionType.English || buyoutPriceReached) {
            auctionService.save(auctionEntity);
        }
        return bidMapper.mapTo(savedBidEntity);
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorizedByBidId(#id)")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBid(@PathVariable("id") String id) {
        try {
            UUID idConverted = UUID.fromString(id);
            bidService.delete(idConverted);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('SELLER') && @userSecurityService.isUserAuthorizedByAuctionId(#auctionId)")
    @GetMapping(path = "/auction/{auctionId}")
    public ResponseEntity<List<BidDto>> getBidsByAuctionId(@PathVariable("auctionId") String auctionId) {
        try {
            UUID id = UUID.fromString(auctionId);
            List<BidEntity> bidEntities = bidService.findByAuctionId(id);
            List<BidDto> result = bidEntities.stream()
                    .map(bidEntity -> bidMapper.mapTo(bidEntity)).toList();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('SELLER') && @userSecurityService.isUserAuthorizedByAuctionId(#bid.getAuctionId())")
    @PostMapping(path = "/chooseWinningBid")
    public ResponseEntity<Void> chooseWinningBid(@RequestBody BidDto bid) {
        try {
            UserEntity winnerEntity = userService.findById(bid.getUserId())
                    .orElseThrow(() -> new RuntimeException("User with id " + bid.getUserId() + NOTFOUND));
            UserDto winner = userMapper.mapTo(winnerEntity);

            AuctionEntity auctionEntity = auctionService.findById(bid.getAuctionId())
                    .orElseThrow(() -> new RuntimeException("Auction with id " + bid.getAuctionId() + NOTFOUND));
            AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);

            for (String token : winner.getDeviceTokens()) {
                String message = "Congratulazioni! Hai vinto l'asta di " + auctionDto.getItem().getName() + "!";
                snsService.sendNotification(token, message);
            }

            List<UserEntity> biddersEntities = auctionService.findBiddersByAuctionId(auctionDto.getId());
            List<UserDto> bidders = biddersEntities.stream()
                    .map(userEntity1 -> userMapper.mapTo(userEntity1)).toList();

            for (UserDto bidder : bidders) {
                if (!bidder.getId().equals(winner.getId())) {
                    for (String token : bidder.getDeviceTokens()) {
                        String message = "Il vincitore dell'asta di " + auctionDto.getItem().getName()
                                + " è stato scelto!\nPurtroppo non hai vinto";
                        snsService.sendNotification(token, message);
                    }
                }
            }
            // eventually the model of Bid can be modified adding a "Winner Bid" field and
            // updated here
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
