package com.shuvi.cinema.controller.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * DTO с информацией токенов авторизации.
 *
 * @author Shuvi
 */
@Data
@Builder
public class AuthResponse {

    @Schema(description = "Токен авторизации.")
    private String access_token;

    @Schema(description = "Токен для обновления авторизации.")
    private String refresh_token;

    @Schema(description = "Информация о созданном пользователе.")
    private UserResponse user;

}
