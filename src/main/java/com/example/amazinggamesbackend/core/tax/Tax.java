package com.example.amazinggamesbackend.core.tax;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Getter
public class Tax {

    private static Tax taxSingleton;
    private Rates[] rates;
    private Tax(){

    }

    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            rates = mapper.readValue(new File("src/main/resources/rates.json") ,Rates[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Tax getInstance() {
        if (taxSingleton == null) {
            taxSingleton = new Tax();
        }
        return taxSingleton;
    }
}
