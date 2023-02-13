package com.example.amazinggamesbackend.core.tax;

import com.example.amazinggamesbackend.config.Tax;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaxReader implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        List<Rates> rates = Tax.readTaxFromFile();
    }
}
