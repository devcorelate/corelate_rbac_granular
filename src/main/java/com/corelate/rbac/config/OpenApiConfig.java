package com.corelate.rbac.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        final String apiKeySchemeName = "apiKey";
        final String bearerSchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info().title("RBAC Service API").version("v1"))
                .addSecurityItem(new SecurityRequirement().addList(apiKeySchemeName).addList(bearerSchemeName))
                .components(new Components()
                        .addSecuritySchemes(apiKeySchemeName,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X-API-Key"))
                        .addSecuritySchemes(bearerSchemeName,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
