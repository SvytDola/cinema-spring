package com.shuvi.cinema.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Запрос на создание пользователя.
 *
 * @author Shuvi
 */
@Data
@Builder
public class UserCreateRequest {

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String email;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private char[] password;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String name;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String surname;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String description;
}
