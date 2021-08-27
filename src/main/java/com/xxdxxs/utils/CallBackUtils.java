package com.xxdxxs.utils;

import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 回调工具类
 * @author xxdxxs
 */
public class CallBackUtils {

    /**
     * 若value为空，则执行回调返回结果
     *
     * @param value
     * @param function
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> V ifNotPresent(V value, Supplier<V> function) {
        return Optional.ofNullable(value).orElseGet(function);
    }


    /**
     * 根据key获取第一个函数的返回值，为空则再返回第二个函数结果
     *
     * @param key
     * @param callBack
     * @param fallBack
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> V ifNotPresent(T key, Function<T, V> callBack, Function<T, V> fallBack) {
        return Optional.ofNullable(callBack.apply(key))
                .orElse(fallBack.apply(key));
    }


}
