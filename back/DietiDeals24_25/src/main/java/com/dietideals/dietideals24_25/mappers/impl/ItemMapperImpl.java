package com.dietideals.dietideals24_25.mappers.impl;

import com.dietideals.dietideals24_25.domain.dto.ItemDto;
import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements Mapper<ItemEntity, ItemDto> {

    private ModelMapper modelMapper;

    public ItemMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemDto mapTo(ItemEntity itemEntity) {
        return modelMapper.map(itemEntity, ItemDto.class);
    }

    @Override
    public ItemEntity mapFrom(ItemDto itemDto) {
        return modelMapper.map(itemDto, ItemEntity.class);
    }
}
