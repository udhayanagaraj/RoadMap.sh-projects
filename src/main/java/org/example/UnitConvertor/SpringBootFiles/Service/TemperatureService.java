package com.unitconvertor.demo.Service;

import com.unitconvertor.demo.Model.DataModel;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService {
    public double convertTemperature(DataModel data) {
        String fromUnit = data.getFrom();
        String toUnit = data.getTo();
        double value = data.getValue();

        // Celsius to Fahrenheit
        if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            return (value * 9 / 5) + 32;
        }
        // Fahrenheit to Celsius
        else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            return (value - 32) * 5 / 9;
        }
        // Celsius to Kelvin
        else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            return value + 273.15;
        }
        // Kelvin to Celsius
        else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            return value - 273.15;
        }
        // Fahrenheit to Kelvin
        else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
            return (value - 32) * 5 / 9 + 273.15;
        }
        // Kelvin to Fahrenheit
        else if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) {
            return (value - 273.15) * 9 / 5 + 32;
        }

        // If the units are not supported
        throw new IllegalArgumentException("Unsupported temperature conversion: " + fromUnit + " to " + toUnit);
    }
}
