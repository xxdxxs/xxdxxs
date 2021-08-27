package com.xxdxxs.annotate;



import java.lang.annotation.*;

/**
 * 实体类属性值的转换，新增一个属性
 * @author xxdxxs
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Additional {

    String column();

    String daoName();

}
