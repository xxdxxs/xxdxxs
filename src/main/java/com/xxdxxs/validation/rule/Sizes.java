package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.utils.NumberUtils;
import com.xxdxxs.validation.RuleChain;
import com.xxdxxs.validation.Validation;
import com.xxdxxs.validation.Validator;
import org.springframework.util.Assert;

/**
 * 数值规则
 * @author xxdxxs
 */
public class Sizes extends Rule {

    private String name;

    private Number min;

    private Number max;

    private boolean isIncluded;

    public Sizes(String name, Number min, Number max, boolean isIncluded) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.isIncluded = isIncluded;
    }

    public static Sizes of(Number value){
        Assert.notNull(value, "value must not be null");
        return new Sizes("", value, value, true);
    }

    public static Sizes of(Number min, Number max, boolean isIncluded){
        Assert.notNull(min, "min must not be null");
        Assert.notNull(max, "max must not be null");
        if (NumberUtils.compareTo(min, max) > 0) {
            Number temp = min;
            min = max;
            max = temp;
        }
        return new Sizes("range", min, max, isIncluded);
    }

    public static Sizes lessThan(Number value){
        Assert.notNull(value, "value must not be null");
        return new Sizes("lessThan", null, value, false);
    }

    public static Sizes greaterThan(Number value){
        Assert.notNull(value, "value must not be null");
        return new Sizes("greaterThan", value, null, false);
    }

    public static Sizes lessThanOrEqual(Number value){
        Assert.notNull(value, "value must not be null");
        return new Sizes("lessThanOrEqual", null , value, true);
    }

    public static Sizes greaterThanOrEqual(Number value){
        Assert.notNull(value, "value must not be null");
        return new Sizes("greaterThanOrEqual", value, null, true);
    }


    @Override
    public void validate(RuleChain ruleChain) {
        Object value = getValue(ruleChain);
        if (Validation.isNumber(value) && NumberUtils.between((Number) value, min, max, isIncluded)) {
            isSuccess(ruleChain);
        } else {
            stop(ruleChain, ValidatorEnum.PARAM_NOT_IN_RANGE);
        }
    }
}
