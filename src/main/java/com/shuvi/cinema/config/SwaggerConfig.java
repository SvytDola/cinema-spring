package com.shuvi.cinema.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author Shuvi
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Кинопоиск",
                version = "1.0.0",
                description = "Сервиса поиска фильмов."
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
public class SwaggerConfig {
}
