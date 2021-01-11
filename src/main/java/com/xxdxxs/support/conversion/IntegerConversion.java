package com.xxdxxs.support.conversion;

import java.util.Optional;

/**
 * @author xxdxxs
 */
public class IntegerConversion implements Conversion<Integer> {

    @Override
    public Optional<Integer> convert(Object value) {
        return Optional.empty();
    }
}
