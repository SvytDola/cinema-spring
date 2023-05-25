package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.auth.AuthLoginRequest;
import com.shuvi.cinema.controller.dto.auth.AuthResponse;
import com.shuvi.cinema.controller.dto.auth.RefreshTokenRequest;
import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.exception.AccessDenied;
import com.shuvi.cinema.service.api.AuthService;
import com.shuvi.cinema.service.api.JwtService;
import com.shuvi.cinema.service.api.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

/**
 * Реализация сервиса авторизации.
 *
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(@NonNull UserCreateRequest body) {
        body.setPassword(passwordEncoder.encode(CharBuffer.wrap(body.getPassword())).toCharArray());
        final UserResponse userResponse = userService.create(body);
        final String token = jwtService.generateToken(body.getEmail());
        final String refreshToken = jwtService.generateRefreshToken(body.getEmail());
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .user(userResponse)
                .build();
    }

    @Override
    public AuthResponse login(@NonNull AuthLoginRequest body) {
        final UserResponse userResponse = userService.getUserByEmail(body.getUsername());

        final String token = jwtService.generateToken(body.getUsername());
        final String refreshToken = jwtService.generateRefreshToken(body.getUsername());

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .user(userResponse)
                .build();
    }

    @Override
    public AuthResponse refreshToken(@NonNull RefreshTokenRequest body) {

        final Claims claims = jwtService.extractClaims(body.getRefreshToken());
        final String email = jwtService.extractUsername(claims);

        if (!jwtService.isTokenValid(claims, email)) {
            throw new AccessDenied();
        }

        final UserResponse userResponse = userService.getUserByEmail(email);

        final String token = jwtService.generateToken(email);

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(body.getRefreshToken())
                .user(userResponse)
                .build();

    }
}
