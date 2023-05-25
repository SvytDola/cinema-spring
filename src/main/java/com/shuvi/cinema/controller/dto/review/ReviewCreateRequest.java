package com.shuvi.cinema.controller.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

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
    @JsonProperty(required = true)
    private UUID cinemaId;

    @NotNull
    @Size(max = 10)
    @JsonProperty(required = true)
    private Integer score;
}
