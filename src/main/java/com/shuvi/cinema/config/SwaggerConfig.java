package com.shuvi.cinema.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

import static com.shuvi.cinema.common.ResourceConstant.LOGIN_API_PATH;

/**
 * Конфигурация для Swagger Open Api.
 *
 * @author Shuvi
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Кинопоиск",
                version = "1.0.0",
                description = "Сервиса поиска фильмов."
        )
)
public class SwaggerConfig {

    private final Environment environment;

    public SwaggerConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public OpenAPI openAPI() {
        return (new OpenAPI())
                .servers(List.of((new Server()).url("/")))
                .components((new Components())
                        .addSecuritySchemes("oauth2", (new io.swagger.v3.oas.models.security.SecurityScheme())
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows((new OAuthFlows())
                                        .password((new OAuthFlow())
                                                .tokenUrl(LOGIN_API_PATH)))))
                .security(List.of((new io.swagger.v3.oas.models.security.SecurityRequirement()).addList("oauth2")))
                .info((new io.swagger.v3.oas.models.info.Info()).title(this.environment.getProperty("application-name", ""))
                        .description(this.environment.getProperty("application-description", ""))
                        .version(this.environment.getProperty("application-version", "v1.0")));

    }

    @Bean
    public OpenAPI openApiConfig() {
        return new SwaggerConfig(environment).openAPI();
    }
}
