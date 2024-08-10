package com.dietideals.dietideals24_25.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import com.dietideals.dietideals24_25.repositories.RoleRepository;
import com.dietideals.dietideals24_25.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<RoleEntity> findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }

}
