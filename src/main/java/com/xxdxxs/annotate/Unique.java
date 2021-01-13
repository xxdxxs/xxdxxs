package com.xxdxxs.annotate;


import java.lang.annotation.*;

/**
 * 确定唯一数据行的字段注解
 * @author xxdxxs
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {
}
