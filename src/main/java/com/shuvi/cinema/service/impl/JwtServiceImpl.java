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

/**
 * @author Shuvi
 */
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${JWT_KEY}")
    private String JWT_KEY;

    @Value("${application.security.jwt.token.expiration}")
    private int tokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private int refreshTokenExpiration;

    @Override
    public Claims extractClaims(@NonNull String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String buildToken(@NonNull String subject, int expiration) {
        final long timestamp = System.currentTimeMillis();
        return Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(@NonNull String id) {
        return buildToken(id, tokenExpiration);
    }

    @Override
    public String generateRefreshToken(@NonNull String id) {
        return buildToken(id, refreshTokenExpiration);
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
        final byte[] keyBytes = Decoders.BASE64.decode(JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
