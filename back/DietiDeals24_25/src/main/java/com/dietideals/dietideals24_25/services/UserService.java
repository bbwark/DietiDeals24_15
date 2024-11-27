package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(UUID id);

    List<UUID> findUserIdsByFavouriteAuctionId(UUID auctionId);

    int addFavouriteAuction(UUID userId, UUID auctionId);

    int removeFavouriteAuction(UUID userId, UUID auctionId);

    Boolean exists(UUID id);

    Optional<UserEntity> findUserByEmail(String email) ;

    void delete(UUID id);

}
