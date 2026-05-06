package com.ravi.orderflow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderFlow API")
                        .version("v1.0.0")
                        .description("OrderFlow is a Spring Boot backend for product, inventory, cart, order, and payment workflows.")
                        .contact(new Contact()
                                .name("Ravi")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server")
                ));
    }
}