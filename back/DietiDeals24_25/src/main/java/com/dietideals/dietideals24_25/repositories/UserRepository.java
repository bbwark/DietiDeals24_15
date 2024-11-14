package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT u.user_id FROM users u JOIN user_favourite_auctions ufa ON u.user_id = ufa.user_id WHERE ufa.auction_id = :auctionId", nativeQuery = true)
    List<UUID> findUserIdsByFavouriteAuctionIdNative(@Param("auctionId") UUID auctionId);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM user_role_junction urj JOIN roles r ON urj.role_id = r.role_id WHERE urj.user_id = :id AND r.authority = :authority)", nativeQuery = true)
    Boolean checkAuthorities(@Param("id") UUID userId, @Param("authority") String authority);
}