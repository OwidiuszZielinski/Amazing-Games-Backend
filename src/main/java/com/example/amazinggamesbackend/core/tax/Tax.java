package com.example.amazinggamesbackend.core.tax;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Getter
public class Tax {
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Rates[] ratesArray;
            ratesArray = mapper.readValue(new File("src/main/resources/rates.json"), Rates[].class);
            rates = Arrays.asList(ratesArray);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Tax taxSingleton;
    private final List<Rates> rates;

    private Tax() {
        if (taxSingleton != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }
    }

    public static Tax getInstance() {
        if (taxSingleton == null) {
            taxSingleton = new Tax();
        }
        return taxSingleton;
    }
}
