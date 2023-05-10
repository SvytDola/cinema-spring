package com.shuvi.cinema.controller.dto.review;

import com.shuvi.cinema.controller.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

/**
 * DTO с информацией о рецензии.
 *
 * @author Shuvi
 */
@Data
public class ReviewResponse {

    @Schema(description = "Идентификатор рецензии.")
    private UUID id;

    @Schema(description = "Сообщение.")
    private String message;

    @Schema(description = "Время создания.")
    private long createdAt;

    @Schema(description = "Время обновления.")
    private long updatedAt;

    @Schema(description = "Информация об авторе рецензии.")
    private UserResponse author;

    @Schema(description = "Идентификатор кино.")
    private UUID cinemaId;

    @Schema(description = "Оценка.")
    private int score;
}
