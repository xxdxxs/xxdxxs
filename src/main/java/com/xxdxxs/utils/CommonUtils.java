package com.xxdxxs.utils;

import java.lang.reflect.Field;
import java.util.*;


/**
 * @Author: xxdxxs
 */
public class CommonUtils {


    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }


    /**
     * 获取类中所有的属性名称
     * @param object
     * @return Field[]
     */
    public static Field[] getFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return fields;
    }

}
