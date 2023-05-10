package com.shuvi.cinema.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Конфигурация для Swagger Open Api.
 *
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
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
//        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
//        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
//                authorizationUrl = AUTH_API_PATH + "/login"
//        ))
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {


}
