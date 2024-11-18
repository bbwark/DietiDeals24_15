package com.dietideals.dietideals24_25.services;

public interface UserSecurityService {
    boolean isUserAuthorized(String targetUserId);
    boolean isUserAuthorizedByAuctionId(String auctionId);
    boolean isUserAuthorizedByBidId(String bidId);
    boolean isUserAuthorizedByItemId(String itemId);
    boolean isUserAuthorizedByCardNumber(String cardNumber);
}
