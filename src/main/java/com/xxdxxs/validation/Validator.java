package com.xxdxxs.validation;

import com.xxdxxs.service.FormHandler;
import com.xxdxxs.validation.rule.Ruler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 校验类
 *
 * @author xxdxxs
 */
public class Validator {

    private Class targetClass;

    private String key;

    private FormHandler formHandler;

    private Map<String, String> attributes;

    private Map<String, RuleChain> ruleChains;

    private Context context;

    private String operateCode;

    public Validator() {
        init();
    }

    public Validator(String jsonStr) {
        new Validator(FormHandler.ofJson(jsonStr));
    }

    private void init() {
        this.ruleChains = new LinkedHashMap();
        this.attributes = new HashMap();
        this.context = new Context();
    }

    public Validator(FormHandler formHandler) {
        init();
        setForm(formHandler);
    }

    public Validator setForm(FormHandler formHandler) {
        this.context.setFormHandler(formHandler);
        return this;
    }

    public FormHandler getForm() {
        return this.context.getFormHandler();
    }

    public Context getContext() {
        return this.context;
    }

    public Validator setAttribute(String key, String name) {
        this.attributes.put(key, name);
        return this;
    }

    public String getAttribute(String key) {
        return this.attributes.get(key);
    }

    public Ruler set(String key, String name) {
        this.attributes.put(key, name);
        if (name != null) {
            this.setAttribute(key, name);
        }
        return ((RuleChain) this.ruleChains.computeIfAbsent(key, (x) -> {
            return new RuleChain(key, this);
        })).getRuler();
    }


    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public Map<String, RuleChain> getRuleChains() {
        return this.ruleChains;
    }

    public String getErrorInfo() {
        return this.context.getErrorMessages();
    }

    public boolean isValid() {
        this.getRuleChains().forEach((k, ruleChain) -> {
            ruleChain.validate();
        });
        boolean pass = context.getResults().values().stream().allMatch(result -> result == true);
        return pass;
    }

    public Validator end() {
        return this;
    }
}
