package com.xxdxxs.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * map工具类
 * @author xxdxxs
 */
public class MapUtils {


    /**
     * 只保留集合中的key
     * @param map
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> retainKeys(Map<String, T> map, List<String> keys){
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
     * @param map
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> removeKeys(Map<String, T> map, List<String> keys){
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
     * @param map
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> removeNullValue(Map<String, T> map){
        map = map.entrySet().stream().filter(x -> x.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return map;
    };

}
