package com.shuvi.cinema.controller.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Запрос на обновление токена авторизации.
 *
 * @author Shuvi
 */
@Data
public class RefreshTokenRequest {

    @NotNull
    @NotBlank
    @JsonProperty("refresh_token")
    private String refreshToken;
}
