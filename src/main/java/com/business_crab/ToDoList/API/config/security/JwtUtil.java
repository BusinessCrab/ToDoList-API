package com.business_crab.ToDoList.API.config.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.lifespan}")
    private long tokenLifeSpan;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private String createToken(final Map<String , Object> claims , final String username) {
        return Jwts.builder()
                   .claims(claims)
                   .subject(username)
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + tokenLifeSpan))
                   .signWith(getSigningKey())
                   .compact();
        
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseEncryptedClaims(token)
                   .getPayload();
    }

    public String generateToken(final UserDetails userDetails) {
        final Map<String , Object> claims = new HashMap<>();
        return createToken(claims , userDetails.getUsername());
    }

    public <T> T extractClaim(final String token , Function<Claims , T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsernameFromToken(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(final String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(final String token , final UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}