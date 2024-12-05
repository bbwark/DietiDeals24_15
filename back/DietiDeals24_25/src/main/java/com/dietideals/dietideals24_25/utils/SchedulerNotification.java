package com.dietideals.dietideals24_25.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    private static final String AUCTIONHEADER = "L'asta di ";

    @Autowired
    public SchedulerNotification(SNSService snsService, AuctionService auctionService, UserService userService,
            Mapper<AuctionEntity, AuctionDto> auctionMapper, Mapper<UserEntity, UserDto> userMapper) {
        this.snsService = snsService;
        this.auctionService = auctionService;
        this.userService = userService;
        this.auctionMapper = auctionMapper;
        this.userMapper = userMapper;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void pollAndNotify() {
        List<AuctionEntity> expiredAuctionsEntities = auctionService.findExpiredAuctions();

        for (AuctionEntity auctionEntity : expiredAuctionsEntities) {
            AuctionDto auction = auctionMapper.mapTo(auctionEntity);
            if (auction.getId() == null) {
                continue;
            }

            Set<UUID> usersInvolvedInAuction = new HashSet<>();
            Set<String> deviceTokensForOwner = new HashSet<>();
            Set<String> deviceTokensForWinner = new HashSet<>();
            Set<String> deviceTokensForUsersInvolved = new HashSet<>();

            // Add owner ID only if not null
            if (auction.getOwnerId() != null) {
                usersInvolvedInAuction.add(auction.getOwnerId());
            }

            // Add users with favorite auctions
            Set<UUID> favoriteUsers = userService.findUserIdsByFavouriteAuctionId(auction.getId()).stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            usersInvolvedInAuction.addAll(favoriteUsers);

            // Add users who bid to auction
            Set<UUID> bidders = auctionService.findBiddersByAuctionId(auction.getId()).stream()
                    .filter(Objects::nonNull)
                    .map(UserEntity::getId)
                    .collect(Collectors.toSet());
            usersInvolvedInAuction.addAll(bidders);

            AtomicBoolean wrapperBuyoutPriceReached = new AtomicBoolean(false);
            BidDto highestBid = findHighestBid(auction, wrapperBuyoutPriceReached);
            boolean buyoutPriceReached = wrapperBuyoutPriceReached.get();

            collectUserTokens(auction, usersInvolvedInAuction, deviceTokensForOwner,
                    deviceTokensForWinner, deviceTokensForUsersInvolved, highestBid);

            notifyOwner(deviceTokensForOwner, auction, highestBid, buyoutPriceReached);

            notifyWinner(deviceTokensForWinner, auction);

            notifyParticipants(deviceTokensForUsersInvolved, auction, buyoutPriceReached);

            auctionEntity.setExpired(true);
            auctionService.save(auctionEntity);
        }
    }

    private void notifyOwner(Set<String> deviceTokensForOwner, AuctionDto auction, BidDto highestBid,
            boolean buyoutPriceReached) {
        for (String token : deviceTokensForOwner) {
            String message;
            String title = "Asta terminata!";
            if (auction.getType().compareTo(AuctionType.English) == 0 ||
                    (auction.getType().compareTo(AuctionType.Silent) == 0 && buyoutPriceReached)) {
                message = AUCTIONHEADER + auction.getItem().getName() + " è terminata!\nL'offerta vincente è di "
                        + highestBid.getValue();
            } else {
                message = AUCTIONHEADER + auction.getItem().getName() + " è terminata!\nControlla subito i risultati!";
            }
            snsService.sendNotification(token, title, message);
        }
    }

    private void notifyWinner(Set<String> deviceTokensForWinner, AuctionDto auction) {
        String message = "Congratulazioni! Hai vinto l'asta di " + auction.getItem().getName() + "!";
        String title = "Hai vinto!";
        for (String token : deviceTokensForWinner) {
            snsService.sendNotification(token, title, message);
        }

    }

    private void notifyParticipants(Set<String> deviceTokensForUsersInvolved, AuctionDto auction,
            boolean buyoutPriceReached) {

        for (String token : deviceTokensForUsersInvolved) {
            String title = "Un'asta è terminata!";
            String message = AUCTIONHEADER + auction.getItem().getName() + " è terminata!\nPresto saprai se hai vinto";
            if (auction.getType().compareTo(AuctionType.English) == 0 ||
                    (auction.getType().compareTo(AuctionType.Silent) == 0 && buyoutPriceReached)) {
                message = AUCTIONHEADER + auction.getItem().getName()
                        + " è terminata!\nPurtroppo questa volta non hai vinto";
            }
            snsService.sendNotification(token, title, message);
        }

    }

    // Protected access for testing
    BidDto findHighestBid(AuctionDto auction, AtomicBoolean buyoutPriceReached) {

        BidDto highestBid = new BidDto();
        highestBid.setValue(0f);

        for (BidDto bid : auction.getBids()) {

            if (bid.getValue() > highestBid.getValue()) {
                highestBid = bid;
            }

            if (auction.getBuyoutPrice() != null && bid.getValue() >= Float.parseFloat(auction.getBuyoutPrice())) {
                buyoutPriceReached.set(true);
            }
        }

        return highestBid;
    }

    private void collectUserTokens(
            AuctionDto auction,
            Set<UUID> usersInvolvedInAuction,
            Set<String> deviceTokensForOwner,
            Set<String> deviceTokensForWinner,
            Set<String> deviceTokensForUsersInvolved,
            BidDto highestBid) {


        addUserDeviceTokens(auction.getOwnerId(), deviceTokensForOwner);

        if (highestBid != null) {
            addUserDeviceTokens(highestBid.getUserId(), deviceTokensForWinner);
        } else {
        }

        usersInvolvedInAuction.stream()
                .filter(userId -> userId != null &&
                        !userId.equals(auction.getOwnerId()) &&
                        (highestBid == null || !userId.equals(highestBid.getUserId())))
                .forEach(userId -> {
                    addUserDeviceTokens(userId, deviceTokensForUsersInvolved);
                });

    }

    private void addUserDeviceTokens(UUID userId, Set<String> deviceTokens) {
        if (userId == null) {
            return;
        }

        userService.findById(userId)
                .map(userMapper::mapTo)
                .map(UserDto::getDeviceTokens)
                .ifPresent(deviceTokens::addAll);
    }

}
