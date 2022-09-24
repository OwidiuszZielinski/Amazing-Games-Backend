package com.example.amazinggamesbackend.core.tax;

public class TaxFactory {

    private static Tax tax;

    public static Tax getInstance() {
        if (tax == null) {
            tax = new Tax();
        }
        return tax;
    }
}
