package com.microservico.estoqueservice.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerGroupConfig {

    @Bean
    public GroupedOpenApi estoqueApi() {
        return GroupedOpenApi.builder()
                .group("estoque")  // Nome do grupo exibido no Swagger UI
                .pathsToMatch("/estoque/**")  // Endpoints que serão documentados
                .build();
    }

    // Exemplo de configuração para endpoints públicos (se necessário no futuro)
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/public/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    if (operation.getOperationId() != null && operation.getOperationId().equals("register")) {
                        operation.addSecurityItem(new SecurityRequirement()); // Remove exigência de auth
                    }
                    return operation;
                })
                .build();
    }
}
