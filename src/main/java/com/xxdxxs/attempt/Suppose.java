package com.xxdxxs.attempt;

import org.springframework.util.ObjectUtils;

public class Suppose<T, V> {

    private T value;

    private V targetValue;

    public Suppose of(T value) {
        return new Suppose(value);
    }


    private Suppose(T value) {
        this.value = value;
    }

    public void is(V targetValue) {
        this.targetValue = targetValue;
    }

    public boolean result() {
        if (ObjectUtils.isEmpty(value) || ObjectUtils.isEmpty(targetValue)) {

        }
        return true;
    }
}
