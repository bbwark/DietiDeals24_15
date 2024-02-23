package com.dietideals.dietideals24_25.mappers.impl;

import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CreditCardMapperImpl implements Mapper<CreditCardEntity, CreditCardDto> {

    private ModelMapper modelMapper;

    public CreditCardMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CreditCardDto mapTo(CreditCardEntity creditCardEntity) {
        return modelMapper.map(creditCardEntity, CreditCardDto.class);
    }

    @Override
    public CreditCardEntity mapFrom(CreditCardDto creditCardDto) {
        return modelMapper.map(creditCardDto, CreditCardEntity.class);
    }
}
