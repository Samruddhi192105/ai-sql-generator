package com.example.aisqlgenerator.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "System.getenv(JWT_SECRET)";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes(StandardCharsets.UTF_8)
            );

    public String generateToken(Long userId) {

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 24
                        )
                )
                .signWith(key)
                .compact();
    }

    public Long extractUserId(String token) {

        String userId =
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject();

        return Long.parseLong(userId);
    }
}
