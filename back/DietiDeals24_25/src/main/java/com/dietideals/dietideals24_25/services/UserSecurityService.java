package com.dietideals.dietideals24_25.services;

import org.springframework.stereotype.Service;

@Service
public interface UserSecurityService {
    boolean isUserAuthorized(String targetUserId);
    boolean isUserAuthorizedByAuctionId(String auctionId);
    boolean isUserAuthorizedByBidId(String bidId);
    boolean isUserAuthorizedByItemId(String itemId);
    boolean isUserAuthorizedByCardNumber(String cardNumber);
}
