package com.shuvi.cinema.service.api;


import com.shuvi.cinema.controller.dto.auth.AuthLoginRequest;
import com.shuvi.cinema.controller.dto.auth.AuthResponse;
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
}
