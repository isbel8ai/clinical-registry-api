package org.isbel8ai.training.clinic.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Clinical Registry API", version = "1.0.0"),
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "Authorization",
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
