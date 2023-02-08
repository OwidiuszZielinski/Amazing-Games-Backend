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

    @Override
    public String toString() {
        return "Rates{" +
                "country_id=" + country_id +
                ", country='" + country + '\'' +
                ", vat_name='" + vat_name + '\'' +
                ", vat_abbr='" + vat_abbr + '\'' +
                ", standard_rate=" + standard_rate +
                ", reduced_rate=" + reduced_rate +
                ", reduced_rate_alt=" + reduced_rate_alt +
                ", super_reduced_rate=" + super_reduced_rate +
                ", parking_rate=" + parking_rate +
                '}';
    }
}
