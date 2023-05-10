package com.shuvi.cinema.controller.dto.auth;

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
    private String token;

    @Schema(description = "Токен для обновления авторизации.")
    private String refreshToken;
}
