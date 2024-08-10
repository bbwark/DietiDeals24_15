package com.dietideals.dietideals24_25.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dietideals.dietideals24_25.domain.entities.RoleEntity;

@Service
public interface RoleService {
    Optional<RoleEntity> findByAuthority(String authority);
}
