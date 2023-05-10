package com.shuvi.cinema.controller.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Запрос на создание рецензии.
 *
 * @author Shuvi
 */
@Data
@Builder
public class ReviewCreateRequest {

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String message;

    @NotNull
    @NotBlank
    @JsonProperty(required = true)
    private String cinemaId;

}
