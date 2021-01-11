package com.xxdxxs.annotate.handle;

import com.xxdxxs.annotate.AbleOperator;
import com.xxdxxs.enums.Operator;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 可用操作符注解
 *
 * @author xxdxxs
 */
public class AbleOperateHandler<E> {
    private static final int INIT_SIZE = 16;


    public Map<String, List<String>> handle(Class clazz) {
        Map<String, List<String>> map = new HashMap<>(INIT_SIZE);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            List<String> list = new ArrayList<>();
            if (field.isAnnotationPresent(AbleOperator.class)) {
                AbleOperator ableOperator = field.getAnnotation(AbleOperator.class);
                Operator[] operators = ableOperator.value();
                Arrays.stream(operators).forEach(e -> list.add(e.getCode()));
            }
            map.put(field.getName(), list);
        }
        return map;
    }
}
