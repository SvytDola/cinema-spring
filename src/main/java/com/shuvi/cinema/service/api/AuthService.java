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
     * @return Токены авторизации.
     */
    AuthResponse register(@NonNull UserCreateRequest body);

    AuthResponse login(@NonNull AuthLoginRequest body);
}
