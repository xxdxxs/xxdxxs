package com.xxdxxs.annotate.handle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义注解处理公共父类
 * @author xxdxxs
 */
public abstract class AnnotationHandler<T extends Annotation> {

    /**
     * 获取被注解的属性集合
     *
     * @param t
     * @param targetClazz
     * @return T extends Annotation
     */
    public <T extends Annotation> List<String> getUsedColumn(Class<T> t, Class targetClazz) {
        List<String> columns = new ArrayList<>();
        Field[] fields = targetClazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(t)) {
                columns.add(field.getName());
            }
        }
        return columns;
    }

}
