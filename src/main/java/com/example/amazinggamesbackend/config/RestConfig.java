package com.example.amazinggamesbackend.config;

import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public GameDayDiscount discount() {
        return new GameDayDiscount();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
