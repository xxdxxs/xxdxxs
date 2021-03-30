package com.xxdxxs.utils;

import com.sun.org.apache.regexp.internal.RE;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * map工具类
 *
 * @author xxdxxs
 */
public class MapUtils {


    /**
     * 只保留集合中的key
     *
     * @param map
     * @param keys
     * @param <T>
     * @return Map<String, T>
     */
    public static <T> Map<String, T> retainKeys(Map<String, T> map, List<String> keys) {
        Map<String, T> resultMap = new HashMap<>();
        map.forEach((k, v) -> {
            if (keys.contains(k)) {
                resultMap.put(k, v);
            }
        });
        return resultMap;
    }


    /**
     * 移除集合中的key
     *
     * @param map
     * @param keys
     * @param <T>
     * @return Map<String, T>
     */
    public static <T> Map<String, T> removeKeys(Map<String, T> map, List<String> keys) {
        Map<String, T> resultMap = new HashMap<>();
        map.forEach((k, v) -> {
            if (!keys.contains(k)) {
                resultMap.put(k, v);
            }
        });
        return resultMap;
    }

    /**
     * 移除map中值为null的键值对
     *
     * @param map
     * @param <T>
     * @return Map<String, T>
     */
    public static <T> Map<String, T> removeNullValue(Map<String, T> map) {
        map = map.entrySet().stream().filter(x -> x.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return map;
    }


    /**
     * 实体类转map
     * @param object
     * @return
     */
    public static Map<String, Object> fromEntity(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);
        try {
            for (Field field : fields) {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object value = field.get(object);
                map.put(field.getName(), value);
                field.setAccessible(flag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
