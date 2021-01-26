package com.xxdxxs.support.conversion;

import com.xxdxxs.validation.Validation;

import java.util.Optional;

/**
 * @author xxdxxs
 */
public class IntegerConversion implements Conversion<Integer> {

    @Override
    public Optional<Integer> convert(Object value) {
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }

        if (value instanceof Integer) {
            return Optional.of((Integer) value);
        }

        if (Validation.isClass(value, int.class)) {
            return Optional.of(Integer.valueOf((int) value));
        }

        if (value instanceof String) {
            return convert((String) value);
        }

        if (value instanceof Number) {
            return Optional.of(((Number) value).intValue());
        }

        return convert(String.valueOf(value));
    }


    public Optional<Integer> convert(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }



}
