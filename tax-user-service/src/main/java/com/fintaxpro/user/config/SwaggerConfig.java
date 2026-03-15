package com.fintaxpro.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI fincore() {
        return new OpenAPI()
                .info(new Info()
                        .title("FinCore Tax Computation API")
                        .description("Enterprise-grade API for computing federal tax, saving computations, and retrieving tax data")
                        .version("1.0.0")
                )
                // GLOBAL SECURITY REQUIREMENT
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

                // SECURITY SCHEME DEFINITION
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
