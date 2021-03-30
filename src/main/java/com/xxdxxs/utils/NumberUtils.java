package com.xxdxxs.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author xxdxxs
 */
public class NumberUtils extends org.springframework.util.NumberUtils {

    public static <T extends Number> int compareTo(T base, T target) {
        if (base == null && target == null) {
            return 0;
        }
        if (base == null) {
            return -1;
        }
        if (target == null) {
            return 1;
        }
        if (base instanceof Byte) {
            return ((Byte) base).compareTo((Byte) target);
        }
        if (base instanceof Integer) {
            return ((Integer) base).compareTo((Integer) target);
        }
        if (base instanceof Long) {
            return ((Long) base).compareTo((Long) target);
        }
        if (base instanceof Short) {
            return ((Short) base).compareTo((Short) target);
        }
        if (base instanceof BigInteger) {
            return ((BigInteger) base).compareTo((BigInteger) target);
        }
        if (base instanceof BigDecimal) {
            return ((BigDecimal) base).compareTo((BigDecimal) target);
        }
        if (base instanceof Double) {
            return ((Double) base).compareTo((Double) target);
        }
        if (base instanceof Float) {
            return ((Float) base).compareTo((Float) target);
        }
        throw new AssertionError();
    }

    public static <T extends Number> boolean between(T value, T min, T max, boolean isIncluded) {
        if (value == null) {
            return false;
        }
        if (min != null && compareTo(value, min) <= (isIncluded ? -1 : 0)) {
            return false;
        }
        return max == null || compareTo(value, max) < (isIncluded ? 1 : 0);
    }

}
