package com.example.amazinggamesbackend.config;

import com.example.amazinggamesbackend.core.tax.Rates;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@NoArgsConstructor
@Getter
public class Tax {


    private static List<Rates> rates;


    public static List<Rates> readTaxFromFile() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream resource = new ClassPathResource("rates.json").getInputStream();
            rates = objectMapper.readValue(resource,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Rates.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rates;
    }
}
