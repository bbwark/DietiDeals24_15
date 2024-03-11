package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService{

    UserEntity registerUser(UserEntity userEntity);
    Optional<UserEntity> findById(UUID id);
    Boolean exists(UUID id);
    Boolean authenticateUser(String email, String password);
    void delete(UUID id);

}
