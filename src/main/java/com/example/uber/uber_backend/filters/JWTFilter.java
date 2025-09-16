package com.example.uber.uber_backend.filters;

import com.example.uber.uber_backend.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTFilter {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.refresh-time}")
    private int refreshTime;

    @Value("${jwt.exp-time}")
    private int expTime;


    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

   public   String generateJWTToken(User user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .signWith(generateSecretKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expTime * 1000L))
                .compact();

    }

    public String generateRefreshToken(User user){
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTime * 1000L))
                .compact();
    }

    private Claims extractClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    public Long getIdFromRefreshToken(String token){
        Claims claims = extractClaimsFromToken(token);
        return  Long.valueOf(claims.getSubject());
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = extractClaimsFromToken(token);
        return claims.get("id", Long.class);
    }
}
