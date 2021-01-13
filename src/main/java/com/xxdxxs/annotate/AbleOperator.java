package com.xxdxxs.annotate;

import com.xxdxxs.enums.Operator;

import java.lang.annotation.*;

/**
 * 定义可执行的规则注解
 * @author xxdxxs
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AbleOperator {
    Operator[] value();
}
