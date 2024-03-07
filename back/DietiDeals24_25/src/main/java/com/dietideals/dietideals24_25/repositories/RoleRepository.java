package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByAuthority(String authority);

}
