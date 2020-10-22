package com.xxdxxs.utils;

public interface TestInterface {

    default String create() {
        System.out.println("in test ------");
        return this.getClass().getName();
    }
}
