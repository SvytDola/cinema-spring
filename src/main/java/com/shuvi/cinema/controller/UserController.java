package com.shuvi.cinema.controller;

import com.shuvi.cinema.controller.dto.user.UserResponse;
import com.shuvi.cinema.service.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.UUID;

import static com.shuvi.cinema.common.ResourceConstant.USER_API_PATH;

/**
 * Контроллер API сущности "User".
 *
 * @author Shuvi
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(USER_API_PATH)
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Получение пользователей.", parameters = {
            @Parameter(name = "start", description = "Номер записи, с которой начинать поиск."),
            @Parameter(name = "size", description = "Количество записей, которое необходимо вернуть."),
    })
    public Collection<UserResponse> findAll(
            @RequestParam(defaultValue = "0") int start,
            @Positive @RequestParam(defaultValue = "25") int size) {
        return userService.findAll(start, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о пользователе по идентификатору.")
    public UserResponse findById(@NonNull @PathVariable UUID id) {
        return userService.findById(id);
    }

    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя по идентафикатору.")
    public void deleteById(@NonNull @PathVariable UUID id) {
        userService.deleteById(id);
    }
}
