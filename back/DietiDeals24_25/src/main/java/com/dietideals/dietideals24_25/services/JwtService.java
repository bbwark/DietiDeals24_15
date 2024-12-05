package com.dietideals.dietideals24_25.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Set;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public interface JwtService {
    String generateToken(String userId, Set<String> roles);

    String extractUserIdFromToken(String token);

    List<String> extractRolesFromToken(String token);

    GoogleIdToken.Payload verifyGoogleIdToken(String googleIdToken) throws GeneralSecurityException, IOException;
}
