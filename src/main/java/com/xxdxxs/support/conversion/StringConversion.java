package com.xxdxxs.support.conversion;

import com.xxdxxs.validation.Validation;

import java.util.Optional;

/**
 * @author xxdxxs
 */
public class StringConversion implements Conversion<String> {

    @Override
    public Optional<String> convert(Object value) {
        if (Validation.isEmpty(value)) {
            return Optional.empty();
        }
        if (value == null) {
            return Optional.empty();
        }
        if (value instanceof CharSequence) {
            value = value.toString();
        }
        if (value instanceof byte[]) {
            value = new String((byte[]) value);
        }
        return Optional.of(String.valueOf(value));
    }
}