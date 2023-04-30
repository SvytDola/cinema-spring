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

    @Schema(description = "Уникальный идентификатор события")
    private UUID id;

    @Schema(description = "Уникальное имя жанра")
    private String name;

    @Schema(description = "Описание жанра")
    private String description;

}
