package io.syncscribe.common.auth;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class Swagger {
    private Swagger() {
    }

    public static OpenAPI openAPI(String title, String description) {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createSecurityScheme()))
                .info(new Info().title(title).description(description)
                        .version("1.0")
                        .contact(new Contact().name("Hoang Nguyen")
                                .email("nguyen@gmail.com"))
                        .license(new License().name("BSD"))
                );
    }

    private static SecurityScheme createSecurityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
