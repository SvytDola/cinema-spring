package com.shuvi.cinema.controller.dto.genre;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

/**
 * DTO c информацией по записи жанра.
 *
 * @author Shuvi
 */
@Data
public class GenreResponse {

    @Schema(description = "Уникальный идентификатор события", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;

    @Schema(description = "Уникальное имя жанра", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Описание жанра", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

}
