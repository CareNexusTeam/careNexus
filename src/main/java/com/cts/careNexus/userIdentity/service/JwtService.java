package com.cts.careNexus.userIdentity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET = "9a4f2c8d3b7a1e5f8c3d2e1a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1a2b3c";

    // Converts the plain-text secret string into a cryptographic HMAC SHA cryptographic key.
    private Key getSignKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Builds a signed JWT token containing the username, role claim, issue time, and a 10-hour expiration.
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 Hours Valid
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Retrieves the subject field (username) from the token's decrypted payload body.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Resolves a specific custom or standard claim from the token payload using a functional strategy.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parses, validates the signature of, and extracts the complete body claims from the incoming token.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Compares the token's internal expiration date against the current system date and time.
    private Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Confirms if the token is structurally valid, targets the matching user, and has not yet expired.
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}