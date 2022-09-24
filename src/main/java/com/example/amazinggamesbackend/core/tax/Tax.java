package com.example.amazinggamesbackend.core.tax;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tax{

    public static List<Rates> taxList = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    Rates[] rates;

    {
        try {
            rates = mapper.readValue(new File("src/main/resources/rates.json"),Rates[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Rates> arrayToList(){
        taxList = Arrays.asList(rates);
        return taxList;
    }


}
