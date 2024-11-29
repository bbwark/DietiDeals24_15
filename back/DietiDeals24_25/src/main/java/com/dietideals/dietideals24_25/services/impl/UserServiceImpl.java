package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UUID> findUserIdsByFavouriteAuctionId(UUID auctionId) {
        return userRepository.findUserIdsByFavouriteAuctionIdNative(auctionId);
    }

    @Override
    public boolean exists(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public int addFavouriteAuction(UUID userId, UUID auctionId) {
        return userRepository.addFavouriteAuction(userId, auctionId);
    }

    @Override
    @Transactional
    public int removeFavouriteAuction(UUID userId, UUID auctionId) {
        return userRepository.removeFavouriteAuction(userId, auctionId);
    }

}
