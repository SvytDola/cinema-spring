package com.shuvi.cinema.service.impl;

import com.shuvi.cinema.controller.dto.auth.AuthLoginRequest;
import com.shuvi.cinema.controller.dto.auth.AuthResponse;
import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.entity.UserEntity;
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
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(@NonNull UserCreateRequest body) {
        body.setPassword(passwordEncoder.encode(body.getPassword()));
        UserResponse userResponse = userService.create(body);
        final String id = userResponse.getId().toString();
        final String token = jwtService.generateToken(id);
        final String refreshToken = jwtService.generateRefreshToken(id);
        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse login(AuthLoginRequest body) {

        UserEntity userEntity = userService.getUserByEmail(body.getEmail());

        return null;

    }
}
