package com.fintaxpro.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    // In real production, move this to config / env variable
    private final String SECRET = "a8f3c9d1e7b2f4a6c3d9e1f7b4a2c6d8a9f1b3c7d5e2f8a4c6d9e3f1b7a5c2";
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_MS = 1000 * 60 * 60; // 1 hour

    // -------------------------
    // 1. GENERATE TOKEN
    // -------------------------
    public String generateToken(String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_MS);

        log.debug("Generating JWT token for email={}, role={}", email, role);

        String token = Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();

        log.info("JWT token generated successfully for email={}", email);
        return token;
    }

    // -------------------------
    // 2. EXTRACT USERNAME
    // -------------------------
    public String extractUsername(String token) {
        log.debug("Extracting username from JWT");
        return extractClaim(token, Claims::getSubject);
    }

    // -------------------------
    // 3. GENERIC CLAIM EXTRACTOR
    // -------------------------
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // -------------------------
    // 4. READ ALL CLAIMS
    // -------------------------
    public Claims extractAllClaims(String token) {
        try {
            log.debug("Validating JWT and extracting claims");
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.warn("Failed to validate JWT token: {}", e.getMessage());
            throw e;
        }
    }
}
