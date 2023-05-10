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

    @Schema(description = "Идентификатор пользователя.")
    private UUID id;

    @Schema(description = "Имя пользователя.")
    private String name;

    @Schema(description = "Фамилия пользователя.")
    private String surname;

    @Schema(description = "Сведения о пользователе.")
    private String description;

    @Schema(description = "Указывает, включен или отключен пользователь. Отключенный пользователь не может быть аутентифицирован.")
    private boolean enabled;

    @Schema(description = "Роли пользователя.")
    private Collection<RoleResponse> roles;
}
