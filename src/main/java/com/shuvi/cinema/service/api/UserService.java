package com.shuvi.cinema.service.api;


import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.entity.UserEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.UUID;

/**
 * Сервис API сущности "User".
 *
 * @author Shuvi
 */
public interface UserService extends UserDetailsService {

    /**
     * Получить информацию о пользователе по почте.
     *
     * @param email Почта пользователя.
     * @return Информацию о найденном пользователе.
     */
    UserResponse getUserByEmail(String email);

    /**
     * Получить информацию о пользователе по идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Информацию о найденном пользователе.
     */
    UserResponse findById(@NonNull UUID id);

    /**
     * Создать пользователя.
     *
     * @param body Детали создоваемого пользователя.
     * @return Информацию о созданном пользователе.
     */
    UserResponse create(@NonNull UserCreateRequest body);

    /**
     * Получить список пользователей.
     *
     * @return Список пользователей.
     */
    Collection<UserResponse> findAll(int start, int size);

    /**
     * Получить текущего авторизованного пользователя.
     *
     * @return Возращает информацию о пользователе.
     */
    UserEntity getCurrentUser();
}
