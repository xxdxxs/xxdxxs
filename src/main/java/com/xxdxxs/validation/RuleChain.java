package com.xxdxxs.validation;


import com.xxdxxs.validation.rule.Rule;
import com.xxdxxs.validation.rule.Ruler;


/**
 * 规则责任链
 *
 * @author xxdxxs
 */
public class RuleChain {

    private String key;

    private Ruler ruler;

    private Validator validator;

    private Rule firstRule;

    private Rule lastRule;

    public RuleChain() {

    }

    public RuleChain(String key, Validator validator) {
        this.key = key;
        this.validator = validator;
        this.ruler = new Ruler(this);

    }

    public void addRule(Rule rule) {
        if (this.firstRule == null) {
            this.firstRule = rule;
        }
        if (this.lastRule == null) {
            this.lastRule = this.firstRule;
        } else {
            this.lastRule.setNextRule(rule);
            this.lastRule = rule;
        }
    }

    public void validate() {
        if (this.firstRule != null) {
            this.firstRule.validate(this);
        }
    }

    public boolean hasKey() {
        return validator.getContext().getFormHandler().getData().containsKey(key);
    }


    public Ruler getNextRuler(String key) {
        return this.getNextRuler(key, null);
    }

    public Ruler getNextRuler(String key, String name) {
        return this.validator.set(key, name);
    }


    public Ruler getRuler() {
        return this.ruler;
    }

    public String getKey() {
        return key;
    }

    public Validator getValidator() {
        return validator;
    }

    public Validator end() {
        return this.validator;
    }

}
