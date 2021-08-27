package com.xxdxxs.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: xxdxxs
 * 获取spring容器中的bean
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextHolder.context = context;
    }


    /**
     * 根据bean name 获取实例
     */
    public static Object getBeanByName(String beanName) {
        if (beanName == null || context == null) {
            return null;
        }
        return context.getBean(beanName);
    }

    /**
     * 只适合一个class只被定义一次的bean（根据class不能匹配出多个该class的实例）
     */
    public static Object getBeanByType(Class clazz) {
        if (clazz == null || context == null) {
            return null;
        }
        return context.getBean(clazz);
    }

    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }

}
