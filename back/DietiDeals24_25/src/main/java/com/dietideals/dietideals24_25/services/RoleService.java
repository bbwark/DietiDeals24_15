package com.dietideals.dietideals24_25.services;

import java.util.Optional;

import com.dietideals.dietideals24_25.domain.entities.RoleEntity;

public interface RoleService {
    Optional<RoleEntity> findByAuthority(String authority);
}
