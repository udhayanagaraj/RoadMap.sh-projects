package com.unitconvertor.demo.Service;


import com.unitconvertor.demo.Model.DataModel;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LengthService {


    private static final Map<String, Double> LENGTH_UNITS_TO_METERS = Map.of(
            "Meter", 1.0,
            "Kilometer", 1000.0,
            "Centimeter", 0.01,
            "Millimeter", 0.001,
            "Mile", 1609.34,
            "Yard", 0.9144,
            "Foot", 0.3048,
            "Inch", 0.0254
    );

    private static final Map<String, Double> METERS_TO_LENGTH_UNITS = Map.of(
            "Meter", 1.0,
            "Kilometer", 0.001,
            "Centimeter", 100.0,
            "Millimeter", 1000.0,
            "Mile", 1 / 1609.34,
            "Yard", 1 / 0.9144,
            "Foot", 1 / 0.3048,
            "Inch", 1 / 0.0254
    );

    public double convertLength(DataModel data){
        validateUnit(data.getFrom());
        validateUnit(data.getTo());

        double valueInMeters = convertToMeters(data.getValue(), data.getFrom());
        return convertFromMeters(valueInMeters, data.getTo());

    }


    private void validateUnit(String unit) {
        if (!LENGTH_UNITS_TO_METERS.containsKey(unit)) {
            throw new IllegalArgumentException("Unknown unit: " + unit);
        }
    }

    private double convertToMeters(double value, String fromUnit) {
        return value * LENGTH_UNITS_TO_METERS.get(fromUnit);
    }

    private double convertFromMeters(double valueInMeters, String toUnit) {
        return valueInMeters * METERS_TO_LENGTH_UNITS.get(toUnit);
    }
}
