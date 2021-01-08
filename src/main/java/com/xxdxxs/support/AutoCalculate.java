package com.xxdxxs.support;

import com.xxdxxs.utils.StringUtils;

import java.io.Serializable;

/**
 * 更新数据时，包装自增自减的字段
 * @author xxdxxs
 */
public class AutoCalculate implements Serializable {
    private Sign sign;

    public enum Sign {
        INCREMENT("+"),
        DECREMENT("-");

        private String sign;

        Sign(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }
    }

    private int value;

    public AutoCalculate(int value) {
        String tempValue = String.valueOf(value);
        if (tempValue.contains(Sign.DECREMENT.getSign())) {
            this.sign = Sign.DECREMENT;
        }else {
            this.sign = Sign.INCREMENT;
        }
        this.value = StringUtils.retainNumber(tempValue);
    }

    public AutoCalculate(Sign sign, int value) {
        this.value = value;
        this.sign = sign;
    }

    public int getValue() {
        return value;
    }

    public String getSign() {
        return this.sign.getSign();
    }
}
