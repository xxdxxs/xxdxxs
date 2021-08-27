package com.xxdxxs.validation.rule;

import com.xxdxxs.enums.ValidatorEnum;
import com.xxdxxs.utils.ConvertUtil;
import com.xxdxxs.validation.RuleChain;
import org.springframework.util.Assert;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 日期规则
 * @author xxdxxs
 */
public class Dates extends Rule {

    private String name;

    private Date min;

    private Date max;

    private SimpleDateFormat formatter;

    private boolean isIncluded;

    private Dates(String name, Date min, Date max, String format, boolean isIncluded) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.formatter = new SimpleDateFormat(format);
        this.isIncluded = isIncluded;
    }

    public static Dates of(Date value, String format){
        Assert.notNull(value, "value must not be null");
        return new Dates("", value, value, format,true);
    }


    public static Dates of(Date min, Date max, String format, boolean isIncluded){
        Assert.notNull(min, "min must not be null");
        Assert.notNull(max, "max must not be null");
        if (max.compareTo(min) < 0) {
            Date temp = max;
            max = min;
            min = temp;
        }
        return new Dates("range", min, max, format, isIncluded);
    }

    public static Dates before(Date value, String format) {
        Assert.notNull(value, "value must not be null");
        return new Dates("before", null, value, format,false);
    }

    public static Dates after(Date value, String format) {
        Assert.notNull(value, "value must not be null");
        return new Dates("after", value, null, format, false);
    }

    public static Dates beforeOrEqual(Date value, String format){
        Assert.notNull(value, "value must not be null");
        return new Dates("beforeOrEqual", null , value, format, true);
    }

    public static Dates afterOrEqual(Date value, String format){
        Assert.notNull(value, "value must not be null");
        return new Dates("afterOrEqual", value, null, format, true);
    }


    @Override
    public void validate(RuleChain ruleChain) {
        Object object = getValue(ruleChain);
        Optional<Date> value = ConvertUtil.DATE.convert(object);
        if (value.isPresent() && validate(value.get())) {
            success(ruleChain);
        } else {
            failed(ruleChain, ValidatorEnum.PARAM_NOT_IN_RANGE);
        }
    }

    private boolean validate(Date date) {
        if (min != null && compare(date, min) <= (isIncluded ? -1 : 0)) {
            return false;
        }
        return max == null || compare(date, max) < (isIncluded ? 1 : 0);
    }

    private int compare(Date base, Date target) {
        formatter.setLenient(true);
        return formatter.format(base).compareTo(formatter.format(target));
    }


}
