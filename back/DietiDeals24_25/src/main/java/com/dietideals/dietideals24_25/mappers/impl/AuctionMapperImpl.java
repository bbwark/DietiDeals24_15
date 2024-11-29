package com.dietideals.dietideals24_25.mappers.impl;

import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuctionMapperImpl implements Mapper<AuctionEntity, AuctionDto> {

    private final ModelMapper modelMapper;
    private final ItemMapperImpl itemMapper;

    public AuctionMapperImpl(ModelMapper modelMapper, ItemMapperImpl itemMapper) {
        this.modelMapper = modelMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public AuctionDto mapTo(AuctionEntity auctionEntity) {
        AuctionDto auctionDto = modelMapper.map(auctionEntity, AuctionDto.class);
        if (auctionEntity.getItem() != null) {
            auctionDto.setItem(itemMapper.mapTo(auctionEntity.getItem()));
        }
        return auctionDto;
    }

    @Override
    public AuctionEntity mapFrom(AuctionDto auctionDto) {
        AuctionEntity auctionEntity = modelMapper.map(auctionDto, AuctionEntity.class);
        if (auctionDto.getItem() != null) {
            auctionEntity.setItem(itemMapper.mapFrom(auctionDto.getItem()));
            auctionEntity.getItem().setAuction(auctionEntity); // Set bidirectional relationship
        }
        return auctionEntity;
    }
}
