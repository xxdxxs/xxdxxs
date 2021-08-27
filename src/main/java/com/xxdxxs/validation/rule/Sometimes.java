package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.RuleType;
import com.xxdxxs.utils.StringUtils;
import com.xxdxxs.validation.RuleChain;
import org.springframework.util.ObjectUtils;

/**
 * 非必要
 *
 * @author xxdxxs
 */
public class Sometimes extends Rule {

    public final static String NAME = RuleType.SOMETIMES.getName();

    public Sometimes() {
    }


    public static Sometimes getInstance() {
        return new Sometimes();
    }

    @Override
    public void validate(RuleChain ruleChain) {
        Object object = getValue(ruleChain);
        if (!ruleChain.hasKey() || StringUtils.isEmpty(object)) {
            stop(ruleChain);
        } else {
            success(ruleChain);
        }
    }
}
