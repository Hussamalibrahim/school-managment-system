package com.SchoolManagementSystem.system.security.jwt;

import com.SchoolManagementSystem.system.entity.Auth.AuthUser;
import com.SchoolManagementSystem.system.exception.model.ErrorCode;
import com.SchoolManagementSystem.system.exception.security.JwtAuthenticationException;
import com.SchoolManagementSystem.system.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//TODO   there no use for this after Microservice
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // CREATE TOKEN
    @Override
    public String generateToken(AuthUser user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("refId", user.getRefId());

        // Multi Tenant
        claims.put("schoolId", user.getSchool().getId());
        claims.put("schoolCode", user.getSchool().getCode());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // EXTRACT USERNAME
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // EXTRACT ROLE
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // EXTRACT REF ID
    public Long extractRefId(String token) {
        return extractAllClaims(token).get("refId", Long.class);
    }

    // VALIDATE
    public boolean isValid(String token, UserDetails userDetails) {
        return extractEmail(token).equals(userDetails.getUsername())
                && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtAuthenticationException(ErrorCode.INVALID_TOKEN);
        }
    }
    @Override
    public Long extractSchoolId(String token) {

        return extractAllClaims(token)
                .get("schoolId", Long.class);
    }


    @Override
    public String extractSchoolCode(String token) {

        return extractAllClaims(token)
                .get("schoolCode", String.class);
    }
}

