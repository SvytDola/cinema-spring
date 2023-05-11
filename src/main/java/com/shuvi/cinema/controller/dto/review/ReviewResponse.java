package com.shuvi.cinema.controller.dto.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shuvi.cinema.controller.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

import java.util.UUID;


/**
 * DTO с информацией о рецензии.
 *
 * @author Shuvi
 */
@Data
@Schema(description = "DTO с информацией о рецензии.", name = "Рецензия")
@JsonInclude(Include.NON_NULL)
public class ReviewResponse {

    @Schema(description = "Идентификатор рецензии.", requiredMode = RequiredMode.REQUIRED)
    private UUID id;

    @Schema(description = "Сообщение.", requiredMode = RequiredMode.REQUIRED)
    private String message;

    @Schema(description = "Время создания.", requiredMode = RequiredMode.REQUIRED)
    private Long createdAt;

    @Schema(description = "Время обновления.", requiredMode = RequiredMode.NOT_REQUIRED)
    private Long updatedAt;

    @Schema(description = "Информация об авторе рецензии.", requiredMode = RequiredMode.REQUIRED)
    private UserResponse author;

    @Schema(description = "Идентификатор кино.", requiredMode = RequiredMode.REQUIRED)
    private UUID cinemaId;

    @Schema(description = "Оценка.", requiredMode = RequiredMode.REQUIRED)
    private Integer score;
}
