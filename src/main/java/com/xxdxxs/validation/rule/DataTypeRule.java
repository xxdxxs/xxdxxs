package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.DataType;
import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.validation.RuleChain;
import com.xxdxxs.validation.Validation;
import com.xxdxxs.validation.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据类型规则类
 *
 * @author xxdxxs
 */
public class DataTypeRule extends Rule {
    private DataType dataType;

    private String format;

    public DataTypeRule(DataType dataType) {
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public static DataTypeRule getInstance(DataType dataType) {
        return new DataTypeRule(dataType);
    }

    public DataTypeRule setFormat(String format) {
        this.format = format;
        return this;
    }

    @Override
    public void validate(RuleChain ruleChain) {
        String key = ruleChain.getKey();
        Validator validator = ruleChain.getValidator();
        System.out.println("key = " + key + "   datatype = " + dataType.getCode());
        Object object = validator.getContext().getFormHandler().getData().get(key);
        boolean pass = dataType.validate(object);
        if (pass) {
            isSuccess(ruleChain);
        } else {
            stop(ruleChain, ValidatorEnum.PARAM_IS_VAILD);
        }
    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        String[] b = new String[]{"1,", "2"};
        Object o = "小李飞刀";
        System.out.println(Validation.isString(o));
    }


}
