package com.microservico.estoqueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "MicroServico Estoque-Service",
                description = "API para gerenciamento de estoque por SKU",
                version = "1.0"
        )
)
@SecurityScheme(
        name = "jwt_auth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class EstoqueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstoqueServiceApplication.class, args);
    }
}
