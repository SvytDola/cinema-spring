package com.shuvi.cinema.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import static com.shuvi.cinema.common.ResourceConstant.LOGIN_API_PATH;

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
        security = @SecurityRequirement(name = "oauth2")
)
@SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        tokenUrl = LOGIN_API_PATH
                )
        )
)
public class SwaggerConfig {

}
