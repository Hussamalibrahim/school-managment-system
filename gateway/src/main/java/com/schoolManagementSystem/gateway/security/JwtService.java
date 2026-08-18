package com.schoolManagementSystem.gateway.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Service
public class JwtService {
    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key =
                Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getRefId(String token) {
        return extractClaims(token).get("refId", Long.class);
    }


    public String getRole(String token) {
        return extractClaims(token).get("role", String.class);
    }


    public Long getSchoolId(String token) {
        return extractClaims(token).get("schoolId", Long.class);
    }

    public boolean isAdminToken(String token) {
        Boolean admin = extractClaims(token).get("admin", Boolean.class);
        return Boolean.TRUE.equals(admin);
    }
}