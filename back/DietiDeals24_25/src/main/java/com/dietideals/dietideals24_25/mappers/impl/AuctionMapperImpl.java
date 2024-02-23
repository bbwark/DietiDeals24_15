package com.dietideals.dietideals24_25.mappers.impl;

import com.dietideals.dietideals24_25.domain.dto.AuctionDto;
import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuctionMapperImpl implements Mapper<AuctionEntity, AuctionDto> {

    private ModelMapper modelMapper;

    public AuctionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuctionDto mapTo(AuctionEntity auctionEntity) {
        return modelMapper.map(auctionEntity, AuctionDto.class);
    }

    @Override
    public AuctionEntity mapFrom(AuctionDto auctionDto) {
        return modelMapper.map(auctionDto, AuctionEntity.class);
    }
}
