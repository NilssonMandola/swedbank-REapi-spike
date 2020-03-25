package com.crankoid.reverseengineeringapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Reverse Engineering API", version = "v1", description = "Fetch data from unofficial APIs"))
public class ReverseEngineeringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReverseEngineeringApiApplication.class, args);
	}

}
