package com.SchoolManagementSystem.System.security.jwt;

import com.SchoolManagementSystem.System.entity.AuthUser;
import com.SchoolManagementSystem.System.exception.business.AuthenticationException;
import com.SchoolManagementSystem.System.exception.model.ErrorCode;
import com.SchoolManagementSystem.System.exception.security.JwtAuthenticationException;
import com.SchoolManagementSystem.System.security.dto.AuthUserDto;
import com.SchoolManagementSystem.System.security.service.JwtService;
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

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // CREATE TOKEN
    @Override
    public String generateToken(AuthUserDto user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.role().name());
        claims.put("refId", user.refId());

        // Multi Tenant
        claims.put("schoolId", user.schoolId());
        claims.put("schoolCode", user.schoolCode());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.email())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 86400000)
                )
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

