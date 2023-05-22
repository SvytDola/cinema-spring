package com.shuvi.cinema.service.api;

import io.jsonwebtoken.Claims;
import org.springframework.lang.NonNull;

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
     * Возвращает новый сгенерированный токен авторизации.
     *
     * @param username Имя или почта пользователя.
     * @return Токен авторизации.
     */
    String generateToken(@NonNull String username);

    /**
     * Возвращает новый сгенерированный токен обновления авторизации.
     *
     * @param username Имя или почта пользователя.
     * @return Токен авторизации.
     */
    String generateRefreshToken(@NonNull String username);

    /**
     * Проверяет валидный ли токен.
     *
     * @param claims Детали авторизации.
     * @return <code>true</code> - если токен валидный, <code>false</code> - если токен не валидный.
     */
    boolean isTokenValid(@NonNull Claims claims, String username);

    /**
     * Получить имя пользователя из деталей авторизации.
     *
     * @param claims Детали авторизации.
     * @return Имя пользователя.
     */
    String extractUsername(Claims claims);
}
