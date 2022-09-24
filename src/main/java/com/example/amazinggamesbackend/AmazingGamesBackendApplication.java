package com.example.amazinggamesbackend;

import com.example.amazinggamesbackend.core.tax.Tax;
import com.example.amazinggamesbackend.core.tax.TaxFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AmazingGamesBackendApplication {

	public static void main(String[] args) {
		Tax tax = TaxFactory.getInstance();
		tax.arrayToList();
		SpringApplication.run(AmazingGamesBackendApplication.class ,args);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/users").allowedMethods("http://localhost:8080");
				registry.addMapping("/games").allowedMethods("http://localhost:8080");
				registry.addMapping("/login").allowedMethods("http://localhost:8080");
				registry.addMapping("/orders").allowedMethods("http://localhost:8080");

			}
		};
	}
}





