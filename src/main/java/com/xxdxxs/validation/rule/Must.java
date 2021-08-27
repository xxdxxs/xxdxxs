package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.utils.StringUtils;
import com.xxdxxs.validation.RuleChain;

/**
 * 必要条件规则类
 *
 * @author xxdxxs
 */
public class Must extends Rule {

    public final static String NAME = "MUST";

    public Must() {
    }

    @Override
    public void validate(RuleChain ruleChain) {
        Object object = getValue(ruleChain);
        if (ruleChain.hasKey() && !StringUtils.isEmpty(object)) {
            success(ruleChain);
        } else {
            failed(ruleChain, ValidatorEnum.PARAM_IS_NULL);
        }
    }

    public static Must getInstance() {
        return new Must();
    }
}
