package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByAuthority(String authority);
}
