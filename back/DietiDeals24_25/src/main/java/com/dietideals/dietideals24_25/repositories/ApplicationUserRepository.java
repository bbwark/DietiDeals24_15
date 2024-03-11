package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, UUID> {
    Optional<ApplicationUser> findByUsername(String username);
}
