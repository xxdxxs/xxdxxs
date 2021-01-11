package com.xxdxxs.enums;

/**
 * @author xxdxxs
 * 数据库操作符号
 */
public enum Operator {

    IS_NULL("IS NULL", "isn", true, false, false, false, false),
    IS_NOT_NULL("IS NOT NULL", "isnn", true, false, false, false, false),
    EQUAL("=", "eq"),
    NOT_EQUAL("!=", "ne"),
    GREATER_THAN(">", "gt", false, false, false, false, true),
    GREATER_THAN_OR_EQUAL(">=", "gte", false, false, false, false, true),
    LESS_THAN("<", "lt", false, false, false, true, false),
    LESS_THAN_OR_EQUAL("<=", "lte", false, false, false, true, false),
    START_WITH("LIKE", "sw"),
    END_WITH("LIKE", "ew"),
    LIKE("LIKE", "lk"),
    IN("IN", "in", false, false, true, false, false),
    NOT_IN("NOT IN", "notin", false, false, true, false, false),
    BETWEEN("BETWEEN", "bt", false, true, false, false, false),
    ;


    private final String sign;
    private final String code;
    private final boolean isValueNullable;
    private final boolean isValuePairable;
    private final boolean isValueListable;
    /**
     * 是否仅仅小于号的范围查询（适用于 <, <=）
     */
    private final boolean isLessRange;

    /**
     * 是否仅仅大于号的范围查询（适用于 >, >=）
     */
    private final boolean isMoreRange;

    private Operator(String sign, String code, boolean isValueNullable, boolean isValuePairable, boolean isValueListable, boolean isLessRange, boolean isMoreRange) {
        this.sign = sign;
        this.code = code;
        this.isValueNullable = isValueNullable;
        this.isValuePairable = isValuePairable;
        this.isValueListable = isValueListable;
        this.isLessRange = isLessRange;
        this.isMoreRange = isMoreRange;
    }

    private Operator(String sign, String code) {
        this.sign = sign;
        this.code = code;
        isValueNullable = false;
        isValuePairable = false;
        isValueListable = false;
        isLessRange = false;
        isMoreRange = false;
    }


    public String getSign() {
        return " " + this.sign + " ";
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

    public boolean isLessRange() {
        return isLessRange;
    }

    public boolean isMoreRange() {
        return isMoreRange;
    }
}
