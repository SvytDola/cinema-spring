package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.auth.AuthLoginRequest;
import com.shuvi.cinema.controller.dto.auth.AuthResponse;
import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.service.api.AuthService;
import com.shuvi.cinema.service.api.JwtService;
import com.shuvi.cinema.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса авторизации.
 *
 * @author Shuvi
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(@NonNull UserCreateRequest body) {
        body.setPassword(passwordEncoder.encode(body.getPassword()));
        UserResponse userResponse = userService.create(body);
        final String token = jwtService.generateToken(body.getEmail());
        final String refreshToken = jwtService.generateRefreshToken(body.getEmail());
        return AuthResponse.builder()
                .access_token(token)
                .refresh_token(refreshToken)
                .user(userResponse)
                .build();
    }

    @Override
    public AuthResponse login(@NonNull AuthLoginRequest body) {
        final UserResponse userResponse = userService.getUserByEmail(body.getUsername());

        final String token = jwtService.generateToken(body.getUsername());
        final String refreshToken = jwtService.generateRefreshToken(body.getUsername());

        return AuthResponse.builder()
                .access_token(token)
                .refresh_token(refreshToken)
                .user(userResponse)
                .build();
    }
}
