package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.dto.LoginDto;

public interface AuthenticationService {
    LoginDto loginUser(String email, String password);
    LoginDto loginWithGoogle(String email, String name);
}
