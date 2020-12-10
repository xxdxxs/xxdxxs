package com.xxdxxs.support.conversion;

import java.util.Optional;

public interface Conversion<T> {

    Optional<T> convert(Object value);
}
