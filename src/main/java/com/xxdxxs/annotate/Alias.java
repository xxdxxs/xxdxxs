package com.xxdxxs.annotate;


import java.lang.annotation.*;

/**
 * 字段别名注解
 * @author xxdxxs
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alias {
    String value();
}
