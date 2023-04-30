package com.shuvi.cinema.controller.dto.genre;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Запрос на создание жанра.
 *
 * @author Shuvi
 */
@Data
@Builder
public class GenreCreateRequest {

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    @Schema(description = "Название жанра")
    private String name;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    @Schema(description = "Описание жанра")
    private String description;

}
