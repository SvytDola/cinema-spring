package com.shuvi.cinema.controller.dto.cinema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuvi.cinema.controller.dto.genre.GenreResponse;
import com.shuvi.cinema.controller.dto.review.ReviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * DTO с информацией по записи кино.
 *
 * @author Shuvi
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CinemaResponse {

    @Schema(description = "Уникальный идентификатор кино", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;

    @Schema(description = "Название кино", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Описание кино", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Длительность кино", requiredMode = Schema.RequiredMode.REQUIRED)
    private long duration;

    @Schema(description = "Жанры кино", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<GenreResponse> genres;

    @Schema(description = "Рецензии кино", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<ReviewResponse> reviews;
}
