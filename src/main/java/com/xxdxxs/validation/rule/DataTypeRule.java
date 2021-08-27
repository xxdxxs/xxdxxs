package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.DataType;
import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.validation.RuleChain;

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
        Object object = getValue(ruleChain);
        boolean pass = dataType.validate(object);
        if (pass) {
            success(ruleChain);
        } else {
            failed(ruleChain, ValidatorEnum.PARAM_TYPE_ERROR);
        }
    }


}
