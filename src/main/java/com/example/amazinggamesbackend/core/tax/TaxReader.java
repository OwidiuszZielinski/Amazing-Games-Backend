package com.example.amazinggamesbackend.core.tax;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaxReader implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Tax.getInstance();
    }
}
