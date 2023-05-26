package com.shuvi.cinema.controller.dto.cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * Запрос на создание кино.
 *
 * @author Shuvi
 */
@Data
@Builder
public class CinemaCreateRequest {

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    @Schema(description = "Название кино")
    private String name;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    @Schema(description = "Описание кино")
    private String description;

    @NotNull
    @JsonProperty(required = true)
    @Schema(description = "Длительность кино")
    private Long duration;

    @NotNull
    @JsonProperty(required = true)
    @Schema(description = "Список идентификаторов жанров кино")
    private List<UUID> genres;

}
