package com.dietideals.dietideals24_25.services.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.dietideals.dietideals24_25.services.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
    private final String jwtSecret;

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
}
