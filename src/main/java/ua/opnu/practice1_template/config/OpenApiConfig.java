package ua.opnu.practice1_template.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    //Налаштування OpenAPI для підтримки JWT-Security в Swagger UI.

    @Bean
    public OpenAPI jwtOpenAPI() {
        return new OpenAPI()

                // Додаємо схему безпеки Bearer JWT
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // Використовуємо HTTP авторизацію
                                        .scheme("bearer") // Схема — Bearer
                                        .bearerFormat("JWT") // Формат — JWT
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearer-jwt", java.util.Collections.emptyList())
                );
    }
}