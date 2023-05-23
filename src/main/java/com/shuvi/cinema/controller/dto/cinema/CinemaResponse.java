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

    @Schema(description = "Уникальный идентификатор кино")
    private UUID id;

    @Schema(description = "Название кино")
    private String name;

    @Schema(description = "Описание кино")
    private String description;

    @Schema(description = "Длительность кино")
    private long duration;

    @Schema(description = "Жанры кино")
    private Set<GenreResponse> genres;

    @Schema(description = "Рецензии кино")
    private Set<ReviewResponse> reviews;
}
