package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.Role;
import com.dietideals.dietideals24_25.domain.entities.UserEntity;
import com.dietideals.dietideals24_25.repositories.UserRepository;
import com.dietideals.dietideals24_25.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("Email not valid"));
    }

    @Override
    public UserEntity registerUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
    @Override
    public Boolean authenticateUser(String email, String password) {
        Optional<UserEntity> userEntity = userRepository.findByEmailAndPassword(email, password);
        return userEntity.isPresent();
    }
    @Override
    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Boolean exists(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }
}
