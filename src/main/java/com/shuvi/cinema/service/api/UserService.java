package com.shuvi.cinema.service.api;


import com.shuvi.cinema.controller.dto.user.UserCreateRequest;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.entity.UserEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Сервис API сущности "User".
 *
 * @author Shuvi
 */
public interface UserService extends UserDetailsService {

    UserEntity getUserByEmail(String email);

    UserResponse create(@NonNull UserCreateRequest body);
}
