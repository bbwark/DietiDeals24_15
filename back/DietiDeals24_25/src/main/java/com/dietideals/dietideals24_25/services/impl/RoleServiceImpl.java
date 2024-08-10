package com.dietideals.dietideals24_25.services.impl;

import java.util.Optional;

import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.repositories.RoleRepository;
import com.dietideals.dietideals24_25.services.RoleService;

public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    
    @Override
    public Optional<RoleEntity> findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }
    
}
