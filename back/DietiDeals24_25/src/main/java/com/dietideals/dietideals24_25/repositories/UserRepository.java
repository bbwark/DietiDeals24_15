package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity WHERE u.email = ?1 AND u.password = ?2")
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

}
