package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.validation.RuleChain;

public class Must extends Rule {

    public final static String NAME = "MUST";

    public Must() {
    }

    @Override
    public void validate(RuleChain ruleChain) {
        if(ruleChain.hasKey()){
            isSuccess(ruleChain);
        }else {
            stop(ruleChain, ValidatorEnum.PARAM_IS_NULL);
        }
    }

    public static Must getInstance(){
        return new Must();
    }
}
