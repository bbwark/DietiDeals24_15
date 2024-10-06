package com.dietideals.dietideals24_25.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dietideals.dietideals24_25.domain.AuctionType;
import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import com.dietideals.dietideals24_25.services.UserService;

@Component
public class SchedulerNotification {

    private final SNSService snsService;
    private final AuctionService auctionService;
    private final UserService userService;
    private final Mapper<AuctionEntity, AuctionDto> auctionMapper;
    private final Mapper<UserEntity, UserDto> userMapper;

    @Autowired
    public SchedulerNotification(SNSService snsService, AuctionService auctionService, UserService userService,
            Mapper<AuctionEntity, AuctionDto> auctionMapper, Mapper<UserEntity, UserDto> userMapper) {
        this.snsService = snsService;
        this.auctionService = auctionService;
        this.userService = userService;
        this.auctionMapper = auctionMapper;
        this.userMapper = userMapper;
    }

    @Scheduled(fixedRate = 60000) // 1 minute
    public void pollAndNotify() {
        System.out.println("Auction expiration check started");
    
        List<AuctionEntity> expiredAuctionsEntities = auctionService.findExpiredAuctions();
        List<AuctionDto> expiredAuctions = expiredAuctionsEntities.stream()
                .map(auction -> auctionMapper.mapTo(auction))
                .collect(Collectors.toList());
    
        for (AuctionDto auction : expiredAuctions) {
            Set<UUID> usersInvolvedInAuction = new HashSet<>();
            Set<String> deviceTokensForOwner = new HashSet<>();
            Set<String> deviceTokensForWinner = new HashSet<>();
            Set<String> deviceTokensForUsersInvolved = new HashSet<>();
            
            usersInvolvedInAuction.add(auction.getOwnerId());
            usersInvolvedInAuction.addAll(userService.findUserIdsByFavouriteAuctionId(auction.getId()));
    
            boolean buyoutPriceReached = false;
            BidDto highestBid = findHighestBid(auction, buyoutPriceReached);
    
            collectUserTokens(auction, usersInvolvedInAuction, deviceTokensForOwner, 
                              deviceTokensForWinner, deviceTokensForUsersInvolved, 
                              highestBid, buyoutPriceReached);
    
            notifyOwner(deviceTokensForOwner, auction, highestBid, buyoutPriceReached);
            notifyWinner(deviceTokensForWinner, auction);
            notifyParticipants(deviceTokensForUsersInvolved, auction, buyoutPriceReached);
    
            auction.setExpired(true);
            auctionService.save(auctionMapper.mapFrom(auction));
        }
    }
    
    private void notifyOwner(Set<String> deviceTokensForOwner, AuctionDto auction, BidDto highestBid, boolean buyoutPriceReached) {
        for (String token : deviceTokensForOwner) {
            String message;
            if (auction.getType().compareTo(AuctionType.English) == 0 || 
                (auction.getType().compareTo(AuctionType.Silent) == 0 && buyoutPriceReached)) {
                message = "L'asta di " + auction.getItem().getName() + " è terminata!\nL'offerta vincente è di " + highestBid.getValue();
            } else {
                message = "L'asta di " + auction.getItem().getName() + " è terminata!\nControlla subito i risultati!";
            }
            snsService.sendNotification(token, message);
        }
    }

    private void notifyWinner(Set<String> deviceTokensForWinner, AuctionDto auction) {
        for (String token : deviceTokensForWinner) {
            String message = "Congratulazioni! Hai vinto l'asta di " + auction.getItem().getName() + "!";
            snsService.sendNotification(token, message);
        }
    }
    
    private void notifyParticipants(Set<String> deviceTokensForUsersInvolved, AuctionDto auction, boolean buyoutPriceReached) {
        for (String token : deviceTokensForUsersInvolved) {
            String message = "L'asta di " + auction.getItem().getName() + " è terminata!\nPresto saprai se hai vinto";
            if (auction.getType().compareTo(AuctionType.English) == 0 || 
                (auction.getType().compareTo(AuctionType.Silent) == 0 && buyoutPriceReached)) {
                message = "L'asta di " + auction.getItem().getName() + " è terminata!\nPurtroppo questa volta non hai vinto";
            }
            snsService.sendNotification(token, message);
        }
    }
    
    private BidDto findHighestBid(AuctionDto auction, boolean buyoutPriceReached) {
        BidDto highestBid = new BidDto();
        highestBid.setValue(0f);
        for (BidDto bid : auction.getBids()) {
            if (bid.getValue() > highestBid.getValue()) {
                highestBid = bid;
            }
            if (auction.getBuyoutPrice() != null && bid.getValue() >= Float.parseFloat(auction.getBuyoutPrice())) {
                buyoutPriceReached = true;
            }
        }
        return highestBid;
    }
    
    private void collectUserTokens(AuctionDto auction, Set<UUID> usersInvolvedInAuction, Set<String> deviceTokensForOwner,
                               Set<String> deviceTokensForWinner, Set<String> deviceTokensForUsersInvolved, 
                               BidDto highestBid, boolean buyoutPriceReached) {
    for (UUID userId : usersInvolvedInAuction) {
        UserEntity userEntity = userService.findById(userId).orElse(null);
        if (userEntity != null) {
            UserDto user = userMapper.mapTo(userEntity);
            if (user.getId().equals(auction.getOwnerId())) {
                user.getDeviceTokens().forEach(token -> deviceTokensForOwner.add(token));
            } else if ((auction.getType().equals(AuctionType.English) || 
                        (auction.getType().equals(AuctionType.Silent) && buyoutPriceReached)) && 
                        user.getId().equals(highestBid.getUserId())) {
                user.getDeviceTokens().forEach(token -> deviceTokensForWinner.add(token));
            } else {
                user.getDeviceTokens().forEach(token -> deviceTokensForUsersInvolved.add(token));
            }
        }
    }
}

}
