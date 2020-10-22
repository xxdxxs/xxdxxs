package com.xxdxxs.annotate;

import com.xxdxxs.enums.Operator;

import java.lang.annotation.*;

/**
 * @author xxdxxs
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AbleOperator {
    Operator[] value();
}
