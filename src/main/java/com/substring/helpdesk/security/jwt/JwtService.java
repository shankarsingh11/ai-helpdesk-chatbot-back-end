package com.substring.helpdesk.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;


    /**
     * Generate JWT using user email.
     */
    public String generateToken(String email) {

        log.debug("Generating JWT for email: {}", email);
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Extract email from JWT.
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Validate JWT.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {

        try {

            String email = extractEmail(token);

            return email != null
                    && email.equals(userDetails.getUsername())
                    && !isTokenExpired(token);

        } catch (JwtException | IllegalArgumentException ex) {

            log.error("Invalid JWT: {}", ex.getMessage());

            return false;
        }
    }


    /**
     * Check whether JWT is expired.
     */
    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /**
     * Extract all JWT claims.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extract Bearer token from Authorization header.
     */
    public String extractToken(String authorizationHeader) {

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return null;
        }

        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }


    /**
     * Build signing key.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
