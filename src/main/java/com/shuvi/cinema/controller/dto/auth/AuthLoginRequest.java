package com.shuvi.cinema.controller.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO для входа в аккаунт.
 *
 * @author Shuvi
 */
@Data
@Builder
public class AuthLoginRequest {

    @JsonProperty(required = true)
    @NotBlank
    @NotNull
    private String username;

    @JsonProperty(required = true)
    @NotBlank
    @NotNull
    private String password;
}
