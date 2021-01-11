package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.DataType;
import com.xxdxxs.validation.RuleChain;
import com.xxdxxs.validation.Validator;

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

    public Ruler date(String format) {
        ruleChain.addRule(DataTypeRule.getInstance(DataType.DATE).setFormat(format));
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
