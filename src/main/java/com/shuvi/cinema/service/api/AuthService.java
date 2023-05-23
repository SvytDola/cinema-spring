package com.shuvi.cinema.service.api;


import com.shuvi.cinema.controller.dto.auth.AuthLoginRequest;
import com.shuvi.cinema.controller.dto.auth.AuthResponse;
import com.shuvi.cinema.controller.dto.auth.RefreshTokenRequest;
import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import org.springframework.lang.NonNull;

/**
 * Сервис авторизации и регистрирования пользователей.
 *
 * @author Shuvi
 */
public interface AuthService {

    /**
     * Зарегестрировать пользователя.
     *
     * @param body Детали создаваемого пользователя.
     * @return Токены авторизации и инофрмация о созданном пользователе.
     */
    AuthResponse register(@NonNull UserCreateRequest body);

    /**
     * Автризация пользователя.
     *
     * @param body Логин и пароль от аккаунта.
     * @return Токены авторизации и инофрмация о пользователе.
     */
    AuthResponse login(@NonNull AuthLoginRequest body);

    /**
     * Обновление токена авторизации.
     *
     * @param body Токен обновления авторизации.
     * @return Токены авторизации и инофрмация о пользователе.
     */
    AuthResponse refreshToken(@NonNull RefreshTokenRequest body);
}
