package com.dietideals.dietideals24_25.config;

import com.dietideals.dietideals24_25.domain.dto.UserDto;
import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Set<RoleEntity>, Set<String>> roleToStringConverter = context ->
                context.getSource().stream()
                        .map(RoleEntity::getAuthority)
                        .collect(Collectors.toSet());

        modelMapper.addMappings(new PropertyMap<UserEntity, UserDto>() {
            @Override
            protected void configure() {
                using(roleToStringConverter).map(source.getAuthorities()).setAuthorities(null);
            }
        });

        return modelMapper;
    }

}
