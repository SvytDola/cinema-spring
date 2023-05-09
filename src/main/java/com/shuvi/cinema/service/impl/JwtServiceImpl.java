package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.service.api.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${JWT_KEY}")
    private String JWT_KEY;

    @Override
    public Claims extractClaims(@NonNull String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String generateToken(@NonNull UserDetails userDetails) {
        final long timestamp = System.currentTimeMillis();
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(@NonNull Claims claims, UserDetails userDetails) {
        final String username = claims.getSubject();
        return username.equals(userDetails.getUsername()) && !isTokenExpired(claims.getExpiration());
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
