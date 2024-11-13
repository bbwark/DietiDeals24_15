package com.dietideals.dietideals24_25.utils.jwtUtilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private final String jwtSecret;

    public JwtTokenProvider() {
        try {
            this.jwtSecret = Base64.getEncoder()
                .encodeToString(SecureRandom.getInstanceStrong()
                .generateSeed(32));
        } catch (NoSuchAlgorithmException e) {
            // Convert to unchecked exception
            throw new SecurityException("Failed to initialize secure random generator", e);
        }
    }

    public String generateToken(String userId) {
        // Inizializza un set di claims con l'ID utente
        Map<String, Object> tokenClaims = new HashMap<>();
        tokenClaims.put("userId", userId);

        // Crea il token JWT con i claims, la firma e la data di scadenza
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
}
