package com.example.amazinggamesbackend.core.tax;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rates {
    private int country_id;
    private String country;
    private String vat_name;
    private String vat_abbr;
    private double standard_rate;
    private double reduced_rate;
    private double reduced_rate_alt;
    private boolean super_reduced_rate;
    private double parking_rate;
}
