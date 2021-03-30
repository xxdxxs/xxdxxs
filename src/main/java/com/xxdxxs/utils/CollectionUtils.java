package com.xxdxxs.utils;


import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 集合工具类
 * @author xxdxxs
 */
public class CollectionUtils {

    /**
     * 按指定大小切割list
     * @param origin
     * @param size
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> divideList(List<T> origin, int size) {
        if (ObjectUtils.isEmpty(origin)) {
            return Collections.emptyList();
        }

        int block = (origin.size() + size - 1) / size;
        return IntStream.range(0, block).boxed().map(i -> {
            int start = i * size;
            int end = Math.min(start + size, origin.size());
            return origin.subList(start, end);
        }).collect(Collectors.toList());
    }


}
