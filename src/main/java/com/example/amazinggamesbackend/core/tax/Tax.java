package com.example.amazinggamesbackend.core.tax;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Getter
public class Tax{

    private static Tax taxSingleton;
    private final List<Rates> rates;
    private Tax(){
        if (taxSingleton != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }
    }
    //Odczyt z pliku poniewaz nie mam dostepu do API zeby sworzyc REST TEMPLATE z stawkami vat
    //Blok try catch kiedy jest wykonywany? kompilacja, wywolanie getInstance?
    //Singleton działa, tworzenie nowej instacji jest zablokowane, aplikacja jest jednowatkowa wiec nie trzeba dodawac synchronizacji
    //jesli implementujemy interefjsy clonable lub serializable musimy to uwzglednic w singletonie bo sa wrazliwe miejsca gdzie moglaby
    //powstac nowa instncja, czy uzywanie getera na getInstance jest poprawne? Musiałem sie dostać do listy rates
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Rates[] ratesArray;
            ratesArray = mapper.readValue(new File("src/main/resources/rates.json") ,Rates[].class);
            rates = Arrays.asList(ratesArray);


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
