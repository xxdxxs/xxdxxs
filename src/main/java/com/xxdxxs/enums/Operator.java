package com.xxdxxs.enums;

/**
 * @author xxdxxs
 * 数据库操作符号
 */
public enum Operator {

    IS_NULL("IS NULL", "isn",true, false, false),
    IS_NOT_NULL("IS NOT NULL", "isnn", true, false, false),
    EQUAL("=", "eq"),
    NOT_EQUAL("!=", "ne"),
    GREATER_THAN(">", "gt"),
    GREATER_THAN_OR_EQUAL(">=", "gte"),
    LESS_THAN("<", "lt"),
    LESS_THAN_OR_EQUAL("<=", "lte"),
    LIKE("LIKE", "lk"),
    IN("IN", "in", false, false, true),
    BETWEEN("BETWEEN", "bt", false, true, false),;



    private final String sign;
    private final String code;
    private final boolean isValueNullable;
    private final boolean isValuePairable;
    private final boolean isValueListable;

    private Operator(String sign, String code, boolean isValueNullable, boolean isValuePairable, boolean isValueListable) {
        this.sign = sign;
        this.code = code;
        this.isValueNullable = isValueNullable;
        this.isValuePairable = isValuePairable;
        this.isValueListable = isValueListable;
    }

    private Operator(String sign, String code) {
        this.sign = sign;
        this.code = code;
        isValueNullable = false;
        isValuePairable = false;
        isValueListable = false;
    }


    public String getSign() {
        return this.sign;
    }

    public String getCode() {
        return code;
    }


    public boolean isValueNullable() {
        return this.isValueNullable;
    }

    public boolean isValuePairable() {
        return this.isValuePairable;
    }

    public boolean isValueListable() {
        return this.isValueListable;
    }
}
