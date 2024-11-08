package com.unitconvertor.demo.Service;

import com.unitconvertor.demo.Model.DataModel;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeightService {

    private static final Map<String, Double> WEIGHT_UNITS_TO_GRAMS = Map.of(
            "Kilogram", 1000.0,
            "Gram", 1.0,
            "Milligram", 0.001,
            "Pound", 453.592,
            "Ounce", 28.3495
    );


    private static final Map<String, Double> GRAMS_TO_WEIGHT_UNITS = Map.of(
            "Kilogram", 0.001,
            "Gram", 1.0,
            "Milligram", 1000.0,
            "Pound", 1 / 453.592,
            "Ounce", 1 / 28.3495
    );


    public double convertWeight(DataModel data) {
        validateUnit(data.getFrom());
        validateUnit(data.getTo());

        double valueInGrams = convertToGrams(data.getValue(), data.getFrom());
        return convertFromGrams(valueInGrams, data.getTo());
    }


    private void validateUnit(String unit) {
        if (!WEIGHT_UNITS_TO_GRAMS.containsKey(unit)) {
            throw new IllegalArgumentException("Unknown weight unit: " + unit);
        }
    }


    private double convertToGrams(double value, String fromUnit) {
        return value * WEIGHT_UNITS_TO_GRAMS.get(fromUnit);
    }


    private double convertFromGrams(double valueInGrams, String toUnit) {
        return valueInGrams * GRAMS_TO_WEIGHT_UNITS.get(toUnit);
    }
}
