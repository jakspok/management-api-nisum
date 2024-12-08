package com.clients.api.rest.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "Juan Carlos Henao",
						url = "https://github.com/jakspok",
						email = "jakspok@gmail.com"
				),
				license = @License(
						name = "MIT Licence")),
		servers = @Server(url = "http://localhost:8080")
)
public class ClientApplication {

	public static void main(String[] args) {

		SpringApplication.run(ClientApplication.class, args);
	}

}
