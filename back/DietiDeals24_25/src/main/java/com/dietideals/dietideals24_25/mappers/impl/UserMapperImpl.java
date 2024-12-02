package com.dietideals.dietideals24_25.mappers.impl;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {

    private ModelMapper modelMapper;
    private AuctionMapperImpl auctionMapper;

    public UserMapperImpl(ModelMapper modelMapper, AuctionMapperImpl auctionMapper) {
        this.modelMapper = modelMapper;
        this.auctionMapper = auctionMapper;
    }

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        if (userEntity.getOwnedAuctions() != null) {
            userDto.setOwnedAuctions(
                    userEntity.getOwnedAuctions().stream()
                            .map(auctionMapper::mapTo).toList());
        }
        if (userEntity.getFavouriteAuctions() != null) {
            userDto.setFavouriteAuctions(
                    userEntity.getFavouriteAuctions().stream()
                            .map(auctionMapper::mapTo).toList());
        }
        return userDto;
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
