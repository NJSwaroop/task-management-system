package com.taskManagementSystem.project.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
	
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Task Management System API")
						.version("1.0")
						.description("Backend API documentation for managing user tasks."))
				.addSecurityItem(new SecurityRequirement().addList("basicAuth"))
				.components(new Components()
						.addSecuritySchemes("basicAuth", new SecurityScheme()
								.name("basicAuth")
								.type(SecurityScheme.Type.HTTP)
								.scheme("basic")));
	}

}
