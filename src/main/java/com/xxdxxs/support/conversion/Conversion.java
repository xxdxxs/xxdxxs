package com.xxdxxs.support.conversion;

import java.util.Optional;

/**
 * @author xxdxxs
 */
public interface Conversion<T> {

    /**
     * 转换数据类型
     *
     * @param value
     * @return
     */
    Optional<T> convert(Object value);
}
