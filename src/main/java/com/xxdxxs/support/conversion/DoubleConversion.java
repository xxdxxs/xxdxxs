package com.xxdxxs.support.conversion;

import com.xxdxxs.validation.Validation;

import java.util.Optional;

public class DoubleConversion implements Conversion<Double>{

    @Override
    public Optional<Double> convert(Object value){
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }
        if (value instanceof Double) {
            return Optional.of((Double) value);
        }
        if (Validation.isClass(value, double.class)) {
            return convert((double) value);
        }
        if (value instanceof Number) {
            return convert((Number) value);
        }
        return convert(String.valueOf(value));
    }

    public Optional<Double> convert(Number value){
        return Optional.of(value.doubleValue());
    }

    public Optional<Double> convert(double value){
        return Optional.of(Double.valueOf(value));
    }

    public Optional<Double> convert(String value){
        return Optional.of(Double.valueOf(value));
    }
}
