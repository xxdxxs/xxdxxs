package com.xxdxxs.support.conversion;

import java.util.Optional;

public class IntegerConversion implements Conversion<Integer> {

    @Override
    public Optional<Integer> convert(Object value) {
        return Optional.empty();
    }
}
