package com.example.amazinggamesbackend.core.tax;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
@Getter
public class Tax implements Cloneable  {

    private static Tax taxSingleton;
    private Rates[] rates;
    private Tax(){
        if (taxSingleton != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }

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
