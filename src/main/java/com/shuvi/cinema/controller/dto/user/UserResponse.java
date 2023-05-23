package com.shuvi.cinema.controller.dto.user;

import com.shuvi.cinema.controller.dto.role.RoleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

/**
 * DTO с информацией о пользователе.
 *
 * @author Shuvi
 */
@Data
public class UserResponse {

    @Schema(description = "Идентификатор пользователя.", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;

    @Schema(description = "Имя пользователя.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Фамилия пользователя.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String surname;

    @Schema(description = "Сведения о пользователе.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Указывает, включен или отключен пользователь. Отключенный пользователь не может быть аутентифицирован",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean enabled;

    @Schema(description = "Роли пользователя.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Collection<RoleResponse> roles;
}
