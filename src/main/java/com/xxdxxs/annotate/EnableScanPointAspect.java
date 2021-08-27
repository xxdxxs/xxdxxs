package com.xxdxxs.annotate;


import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * 使jar里面的bean可以被使用
 * @author xxdxxs
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({BasePointAspectConfig.class})
public @interface EnableScanPointAspect {

}
