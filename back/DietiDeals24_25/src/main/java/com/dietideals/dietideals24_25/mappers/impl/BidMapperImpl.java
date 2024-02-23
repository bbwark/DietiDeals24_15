package com.dietideals.dietideals24_25.mappers.impl;

import com.dietideals.dietideals24_25.domain.dto.BidDto;
import com.dietideals.dietideals24_25.domain.entities.BidEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BidMapperImpl implements Mapper<BidEntity, BidDto> {

    private ModelMapper modelMapper;

    public BidMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BidDto mapTo(BidEntity bidEntity) {
        return modelMapper.map(bidEntity, BidDto.class);
    }

    @Override
    public BidEntity mapFrom(BidDto bidDto) {
        return modelMapper.map(bidDto, BidEntity.class);
    }
}
