package com.example.authservice.service;

import com.example.authservice.model.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Service
public class JwtService {
    String secretKey = "my0320character0ultra0secure0and0ultra0long0secret";

    Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(signingKey())
                .build().parseSignedClaims(token).getPayload();
    }

    Boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    String generateToken(AppUser user) {
        return Jwts.builder().subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(signingKey())
                .compact();
    }

    private SecretKey signingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
