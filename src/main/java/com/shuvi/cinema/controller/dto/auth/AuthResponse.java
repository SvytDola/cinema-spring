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
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "Токен для обновления авторизации.")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Schema(description = "Информация о созданном пользователе.")
    private UserResponse user;

}
