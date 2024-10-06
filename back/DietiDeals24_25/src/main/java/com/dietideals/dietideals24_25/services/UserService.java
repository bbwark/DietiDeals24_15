package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(UUID id);

    List<UUID> findUserIdsByFavouriteAuctionId(UUID auctionId);

    Boolean exists(UUID id);

    Boolean authenticateUser(String email, String password);

    void delete(UUID id);

}
