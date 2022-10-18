package com.example.amazinggamesbackend.interfaces;

import java.text.DecimalFormat;

public interface FormatValue {
    default double format(double value){
        DecimalFormat formatValue = new DecimalFormat("##.00");
        return Double.parseDouble(formatValue.format(value).replace("," ,"."));
    }
}
