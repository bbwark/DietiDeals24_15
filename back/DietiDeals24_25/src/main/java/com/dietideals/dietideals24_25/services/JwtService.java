package com.dietideals.dietideals24_25.services;

import java.util.List;
import java.util.Set;

public interface JwtService {
    String generateToken(String userId, Set<String> roles);

    String extractUserIdFromToken(String token);

    List<String> extractRolesFromToken(String token);
}
