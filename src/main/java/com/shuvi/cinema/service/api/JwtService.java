package com.shuvi.cinema.service.api;

import io.jsonwebtoken.Claims;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Сервис jwt.
 *
 * @author Shuvi
 */
public interface JwtService {

    /**
     * Возвращает имя пользовательзя из токена авторизации.
     *
     * @param token Токен авторизации.
     * @return Данные, хранящиеся в токене авторизации.
     */
    Claims extractClaims(@NonNull String token);

    /**
     * Возвращает новый сгенерированный токен авторидзации.
     *
     * @param username Имя или почта пользователя.
     * @return Токен авторизации.
     */
    String generateToken(@NonNull String username);

    String generateRefreshToken(@NonNull String username);

    /**
     * Проверяет валидный ли токен.
     *
     * @param claims авторизации.
     * @return <code>true</code> - если токен валидный, <code>false</code> - если токен не валидный.
     */
    boolean isTokenValid(@NonNull Claims claims, UserDetails userDetails);
}
