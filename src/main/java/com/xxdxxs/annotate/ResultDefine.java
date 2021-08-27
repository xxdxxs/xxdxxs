package com.xxdxxs.annotate;


import java.lang.annotation.*;

/**
 * 结果自定义
 * @author xxdxxs
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResultDefine {

    Class<?> type();

    String filter() default "";
}
