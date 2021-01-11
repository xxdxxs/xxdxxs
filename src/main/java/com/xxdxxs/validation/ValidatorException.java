package com.xxdxxs.validation;

public class ValidatorException {

    private String key;

    private String code;

    private String name;

    private String msg;

    public ValidatorException(String key, String code, String name) {
        this.key = key;
        this.code = code;
        this.name = name;
    }

    public ValidatorException(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getMsg() {
        this.msg = this.key + ": " + this.name;
        return msg;
    }
}
