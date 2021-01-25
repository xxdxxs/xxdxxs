package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.utils.ConvertUtil;
import com.xxdxxs.validation.RuleChain;
import com.xxdxxs.validation.Validation;
import com.xxdxxs.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 规则抽象类
 *
 * @author xxdxxs
 */
public abstract class Rule {

    public Rule nextRule;

    public Rule() {
    }

    public void setNextRule(Rule rule) {
        this.nextRule = rule;
    }

    public abstract void validate(RuleChain ruleChain);

    public void stop(RuleChain ruleChain, ValidatorEnum validatorEnum) {
        isFail(ruleChain, validatorEnum);
    }

    public void isSuccess(RuleChain chain) {
        executeNext(chain);
    }

    public void isFail(RuleChain ruleChain, ValidatorEnum validatorEnum) {
        Validator validator = ruleChain.getValidator();
        Map<String, List<String>> messages = validator.getContext().getMessage();
        Map<RuleChain, Boolean> results = validator.getContext().getResults();
        String message = validator.getAttribute(ruleChain.getKey()) + validatorEnum.getMsg();
        messages.computeIfAbsent(ruleChain.getKey(), (x) -> {
            return new ArrayList<String>();
        }).add(message);
        results.put(ruleChain, Boolean.FALSE);
        executeNext(ruleChain);
    }

    public void executeNext(RuleChain chain) {
        if (nextRule != null) {
            this.nextRule.validate(chain);
        }
    }

    protected Object getValue(RuleChain ruleChain) {
        String key = ruleChain.getKey();
        Validator validator = ruleChain.getValidator();
        Object object = validator.getContext().getFormHandler().getData().get(key);
        return object;
    }

    protected void execute(RuleChain ruleChain, Object value, Function<Object, Boolean> function) {
        Boolean flag = function.apply(value);
        if (flag) {
            isSuccess(ruleChain);
        } else {
            stop(ruleChain, ValidatorEnum.PARAM_IS_VAILD);
        }
    }
}
