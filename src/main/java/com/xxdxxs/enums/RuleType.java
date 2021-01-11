package com.xxdxxs.enums;

/**
 * @author xxdxxs
 */

public enum RuleType {

    SOMETIMES("sometimes"),
    MUST("must");


    private String name;

    RuleType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
