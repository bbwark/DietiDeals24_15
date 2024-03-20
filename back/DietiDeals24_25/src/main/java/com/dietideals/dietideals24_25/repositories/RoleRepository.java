package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findByAuthority(String authority);
}
