package com.dietideals.dietideals24_25.services;

import org.springframework.stereotype.Service;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;

@Service
public interface AuthenticationService {
    LoginDto loginUser(String email, String password);
}
