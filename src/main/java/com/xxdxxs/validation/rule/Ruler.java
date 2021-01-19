package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.DataType;
import com.xxdxxs.validation.RuleChain;
import com.xxdxxs.validation.Validator;

import java.util.Date;

/**
 * 校验的规则方法定义
 * @author xxdxxs
 */
public class Ruler {

    private RuleChain ruleChain;

    public Ruler(RuleChain ruleChain) {
        this.ruleChain = ruleChain;
    }

    public Ruler sometimes() {
        ruleChain.addRule(Sometimes.getInstance());
        return this;
    }

    public Ruler must() {
        ruleChain.addRule(Must.getInstance());
        return this;
    }

    public Ruler string() {
        return setDataType(DataType.STRING);
    }

    public Ruler number() {
        return setDataType(DataType.NUMBER);
    }

    public Ruler list() {
        return setDataType(DataType.LIST);
    }

    public Ruler date() {
        return setDataType(DataType.DATE);
    }

    public Ruler map() {
        return setDataType(DataType.MAP);
    }

    public Ruler integer() {
        return setDataType(DataType.INTEGER);
    }

    public Ruler date(String format) {
        ruleChain.addRule(DataTypeRule.getInstance(DataType.DATE).setFormat(format));
        return this;
    }

    public Ruler before(Date date, String format) {
        ruleChain.addRule(Dates.before(date, format));
        return this;
    }

    public Ruler before(Date date) {
        return before(date, "yyyy-MM-dd");
    }

    public Ruler beforeOrEqual(Date date, String format) {
        ruleChain.addRule(Dates.beforeOrEqual(date, format));
        return this;
    }

    public Ruler beforeOrEqual(Date date) {
        return beforeOrEqual(date, "yyyy-MM-dd");
    }

    public Ruler after(Date date, String format) {
        ruleChain.addRule(Dates.after(date, format));
        return this;
    }

    public Ruler after(Date date) {
        return after(date, "yyyy-MM-dd");
    }

    public Ruler afterOrEqual(Date date, String format) {
        ruleChain.addRule(Dates.afterOrEqual(date, format));
        return this;
    }

    public Ruler afterOrEqual(Date date) {
        return afterOrEqual(date, "yyyy-MM-dd");
    }

    public <T extends Number> Ruler smaller(T max) {
        return smaller(max, false);
    }

    public <T extends Number> Ruler smaller(T max,  boolean equal) {
        ruleChain.addRule(equal? Sizes.lessThanOrEqual(max) : Sizes.lessThan(max));
        return this;
    }

    public <T extends Number> Ruler larger(T min) {
        return larger(min, false);
    }

    public <T extends Number> Ruler larger(T min,  boolean equal) {
        ruleChain.addRule(equal? Sizes.greaterThanOrEqual(min) : Sizes.greaterThan(min));
        return this;
    }


    private Ruler setDataType(DataType dataType) {
        ruleChain.addRule(DataTypeRule.getInstance(dataType));
        return this;
    }

    public Ruler set(String key, String name) {
        return this.ruleChain.getNextRuler(key, name);
    }

    public Validator end() {
        return this.ruleChain.end();
    }
}
