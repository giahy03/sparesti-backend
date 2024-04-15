package edu.ntnu.idatt2106.sparesti.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Class used to configure Swagger.
 */
@OpenAPIDefinition(

    info = @Info(
        title = "QuizApp API",
        version = "1.0",
        description = "API for SpareSti, a personal economy app. The app is part of the final " +
            "project of IDATT-2106 Systemutvikling 2.",
        contact = @Contact(
            email = "tobiasof@ntnu.no"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local server"
        ),
    }
)
public class SwaggerConfig {

}