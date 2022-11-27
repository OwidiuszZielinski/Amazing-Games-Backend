package com.example.amazinggamesbackend.config;

import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig {
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

    @Bean
    public GameDayDiscount discount (){
        return new GameDayDiscount();
    }
    @Bean
    public RestTemplate restTemplate (){
        return new RestTemplate();
    }

}
