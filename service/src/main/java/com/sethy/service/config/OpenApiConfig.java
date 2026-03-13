package com.sethy.service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI}")
    private String issuerUri;

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("OAuth2 JWT Bearer Token")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        return new OpenAPI()
                .info(new Info()
                        .title("Auth Platform API")
                        .version("1.0.0")
                        .description("Authentication platform API with OAuth2/JWT security")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("/").description("Default Server")
                ))
                .components(new Components().addSecuritySchemes("bearerAuth", bearerAuth));
    }
}
