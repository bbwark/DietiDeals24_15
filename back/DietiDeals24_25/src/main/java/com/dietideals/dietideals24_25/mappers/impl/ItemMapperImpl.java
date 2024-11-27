package com.dietideals.dietideals24_25.mappers.impl;

import com.dietideals.dietideals24_25.domain.dto.ItemDto;
import com.dietideals.dietideals24_25.domain.entities.ItemEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements Mapper<ItemEntity, ItemDto> {

    private ModelMapper modelMapper;
    private static final String DELIMITER = " ";

    public ItemMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemDto mapTo(ItemEntity itemEntity) {
        ItemDto itemDto = modelMapper.map(itemEntity, ItemDto.class);
        if (itemEntity.getImageUrl() != null) {
            itemDto.setImageUrl(new ArrayList<>(Arrays.asList(itemEntity.getImageUrl().split(DELIMITER))));
        }
        return itemDto;
    }

    @Override
    public ItemEntity mapFrom(ItemDto itemDto) {
        ItemEntity itemEntity = modelMapper.map(itemDto, ItemEntity.class);
        if (itemDto.getImageUrl() != null && !itemDto.getImageUrl().isEmpty()) {
            itemEntity.setImageUrl(String.join(DELIMITER, itemDto.getImageUrl()));
        }
        return itemEntity;
    }
}
