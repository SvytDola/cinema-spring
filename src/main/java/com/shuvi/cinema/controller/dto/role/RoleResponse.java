package com.shuvi.cinema.controller.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO с информацией о роли.
 *
 * @author Shuvi
 */
@Data
public class RoleResponse {

    @Schema(description = "Идентификатор роли.")
    private Long id;

    @Schema(description = "Название роли.")
    private String name;
}
