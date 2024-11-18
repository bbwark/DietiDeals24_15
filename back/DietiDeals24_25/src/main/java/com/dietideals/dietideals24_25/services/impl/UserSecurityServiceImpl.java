package com.dietideals.dietideals24_25.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.dto.ItemDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import com.dietideals.dietideals24_25.services.BidService;
import com.dietideals.dietideals24_25.services.CreditCardService;
import com.dietideals.dietideals24_25.services.ItemService;
import com.dietideals.dietideals24_25.services.UserSecurityService;

import jakarta.transaction.Transactional;

import java.util.UUID;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private BidService bidService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private Mapper<AuctionEntity, AuctionDto> auctionMapper;
    @Autowired
    private Mapper<BidEntity, BidDto> bidMapper;
    @Autowired
    private Mapper<ItemEntity, ItemDto> itemMapper;
    @Autowired
    private Mapper<CreditCardEntity, CreditCardDto> creditCardMapper;

    @Override
    public boolean isUserAuthorized(String targetUserId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserDto) {
                UserDto user = (UserDto) auth.getPrincipal();
                return user.getId().equals(UUID.fromString(targetUserId));
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isUserAuthorizedByAuctionId(String auctionId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserDto) {
                AuctionEntity auctionEntity = auctionService.findById(UUID.fromString(auctionId)).orElse(null);
                if (auctionEntity != null) {
                    AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);
                    return auctionDto.getOwnerId().equals(((UserDto) auth.getPrincipal()).getId());
                }
            }

            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isUserAuthorizedByBidId(String bidId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserDto) {
                BidEntity bidEntity = bidService.findById(UUID.fromString(bidId)).orElse(null);
                if (bidEntity != null) {
                    BidDto bidDto = bidMapper.mapTo(bidEntity);
                    return bidDto.getUserId().equals(((UserDto) auth.getPrincipal()).getId());
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean isUserAuthorizedByItemId(String itemId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserDto) {
                ItemEntity itemEntity = itemService.findById(UUID.fromString(itemId)).orElse(null);
                if (itemEntity != null) {
                    ItemDto itemDto = itemMapper.mapTo(itemEntity);
                    AuctionEntity auctionEntity = auctionService.findById(itemDto.getAuctionId()).orElse(null);
                    if (auctionEntity != null) {
                        AuctionDto auctionDto = auctionMapper.mapTo(auctionEntity);
                        return auctionDto.getOwnerId().equals(((UserDto) auth.getPrincipal()).getId());
                    }
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean isUserAuthorizedByCardNumber(String cardNumber) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserDto) {
                CreditCardEntity creditCardEntity = creditCardService.findByCardNumber(cardNumber).orElse(null);
                if (creditCardEntity != null) {
                    CreditCardDto creditCardDto = creditCardMapper.mapTo(creditCardEntity);
                    return creditCardDto.getOwnerId().equals(((UserDto) auth.getPrincipal()).getId());
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
