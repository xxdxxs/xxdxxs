package com.xxdxxs.support.conversion;

import com.xxdxxs.validation.Validation;

import java.util.Optional;

/**
 * @author xxdxxs
 */
public class FloatConversion implements Conversion<Float> {

    @Override
    public Optional<Float> convert(Object value) {
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }
        if (value instanceof Number) {
            return convert((Number) value);
        }
        if (Validation.isClass(value, float.class)) {
            return convert((float) value);
        }
        return convert(String.valueOf(value));
    }

    public Optional<Float> convert(Number value) {
        return Optional.of(value.floatValue());
    }

    public Optional<Float> convert(float value) {
        return Optional.of(Float.valueOf(value));
    }

    public Optional<Float> convert(String value) {
        return Optional.of(Float.valueOf(value));
    }
}
