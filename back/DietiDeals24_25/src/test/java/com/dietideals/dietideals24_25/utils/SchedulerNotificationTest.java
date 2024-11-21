package com.dietideals.dietideals24_25.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.AuctionService;
import com.dietideals.dietideals24_25.services.UserService;

class SchedulerNotificationTest {

    @Mock
    private SNSService snsService;

    @Mock
    private AuctionService auctionService;

    @Mock
    private UserService userService;

    @Mock
    private Mapper<AuctionEntity, AuctionDto> auctionMapper;

    @Mock
    private Mapper<UserEntity, UserDto> userMapper;

    @InjectMocks
    private SchedulerNotification schedulerNotification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findHighestBid_NoBids() {
        AuctionDto auction = new AuctionDto();
        auction.setBids(new ArrayList<>());
        AtomicBoolean buyoutPriceReached = new AtomicBoolean(false);

        BidDto highestBid = schedulerNotification.findHighestBid(auction, buyoutPriceReached);

        assertEquals(0f, highestBid.getValue());
        assertTrue(!buyoutPriceReached.get());
    }

    @Test
    void findHighestBid_WithBids() {
        AuctionDto auction = new AuctionDto();
        BidDto bid1 = new BidDto();
        bid1.setValue(100f);
        BidDto bid2 = new BidDto();
        bid2.setValue(200f);
        auction.setBids(new ArrayList<>(Arrays.asList(bid1, bid2)));
        AtomicBoolean buyoutPriceReached = new AtomicBoolean(false);

        BidDto highestBid = schedulerNotification.findHighestBid(auction, buyoutPriceReached);

        assertEquals(200f, highestBid.getValue());
        assertTrue(!buyoutPriceReached.get());
    }

    @Test
    void findHighestBid_BuyoutPriceReached() {
        AuctionDto auction = new AuctionDto();
        auction.setBuyoutPrice("150");
        BidDto bid1 = new BidDto();
        bid1.setValue(100f);
        BidDto bid2 = new BidDto();
        bid2.setValue(200f);
        auction.setBids(new ArrayList<>(Arrays.asList(bid1, bid2)));
        AtomicBoolean buyoutPriceReached = new AtomicBoolean(false);

        BidDto highestBid = schedulerNotification.findHighestBid(auction, buyoutPriceReached);

        assertEquals(200f, highestBid.getValue());
        assertTrue(buyoutPriceReached.get());
    }
}