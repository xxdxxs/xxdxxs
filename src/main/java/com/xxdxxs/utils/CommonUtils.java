package com.xxdxxs.utils;

import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


/**
 * @Author: xxdxxs
 * @Date: Created in 9:47 2019/4/3
 * @Description:
 */
public class CommonUtils {


    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }


    /**
     * 获取类中所有的属性名称
     */
    public static Field[] getFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        return fields;
    }


    /**
     * 获取类中所有的属性名称(包含父类)
     */
    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    public static boolean isAllNotNull(Object... args) {
        boolean flag = true;
        for (Object object : args) {
            if (StringUtils.isEmpty(object)) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    public static <T> void ifPresent(String key, T value, BiConsumer<String, T> consumer) {
        if (!StringUtils.isEmpty(value)) {
            consumer.accept(key, value);
        }
    }


    public static <T> void ifPresent(T value, Consumer<T> consumer) {
        if (!StringUtils.isEmpty(value)) {
            consumer.accept(value);
        }
    }

}
