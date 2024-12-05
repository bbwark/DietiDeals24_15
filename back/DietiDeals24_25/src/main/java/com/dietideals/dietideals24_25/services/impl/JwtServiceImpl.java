package com.dietideals.dietideals24_25.services.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import javax.crypto.SecretKey;

import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

import com.dietideals.dietideals24_25.services.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
    private final String jwtSecret;
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public JwtServiceImpl() {
        try {
            this.jwtSecret = Base64.getEncoder()
                    .encodeToString(SecureRandom.getInstanceStrong()
                            .generateSeed(32));
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("Failed to initialize secure random generator", e);
        }
    }

    @Override
    public String generateToken(String userId, Set<String> roles) {
        Map<String, Object> tokenClaims = new HashMap<>();
        tokenClaims.put("userId", userId);
        tokenClaims.put("roles", roles);

        return Jwts
                .builder()
                .claims(tokenClaims)
                .issuedAt(new Date())
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUserIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", String.class);
    }

    @SuppressWarnings("unchecked") //Safe cast
    @Override
    public List<String> extractRolesFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", List.class);
    }

    @Override
    public GoogleIdToken.Payload verifyGoogleIdToken(String googleIdToken) throws GeneralSecurityException, IOException{
        GoogleIdTokenVerifier googleVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JSON_FACTORY)
                .setAudience(Collections.singletonList("CLIENT_ID"))
                .build();
        GoogleIdToken idToken = googleVerifier.verify(googleIdToken);
        if(idToken != null) {
            return idToken.getPayload();
        } else {
            throw new GeneralSecurityException("Failed to verify google id token");
        }
    }
}
