package com.xxdxxs.utils;

import java.util.function.Function;

/**
 * 回调工具类
 * @author xxdxxs
 */
public class CallBackUtils {

    /**
     * 根据key获取的value，若value为空，则执行回调返回结果
     * @param key
     * @param value
     * @param function
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> V ifNotPresent(T key, V value, Function<T, V> function) {
        if (StringUtils.isEmpty(value)) {
            return function.apply(key);
        }
        return value;
    }


}
