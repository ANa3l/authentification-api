package com.api.authentification.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Swagger / OpenAPI.
 * Cette classe configure la documentation de l'API avec Springdoc.
 * Elle ajoute un schéma de sécurité JWT de type Bearer pour permettre
 * l'authentification via Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Initialise et configure le schéma OpenAPI avec une sécurité JWT.
     * @return configuration de l'API pour Swagger UI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("Authentification API")
                        .version("1.0")
                        .description("Documentation de l'API d'authentification"));
    }
}
