package com.xxdxxs.db.querier;

import com.xxdxxs.support.AutoCalculate;
import com.xxdxxs.support.Column;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.Map;


/**
 * 属性设置接口类
 *
 * @author xxdxxs
 */
public interface FeaturedSetter<T> extends Setter<T, Column> {

    default T set(String key, Serializable value, int type) {
        return set(new Column(key, value, type));
    }

    default T set(String key, String value) {
        return set(key, value, Types.VARCHAR);
    }

    default T set(String key, Integer value) {
        return set(key, value, Types.INTEGER);
    }

    default T set(String key, Long value) {
        return set(key, value, Types.INTEGER);
    }

    default T set(String key, Float value) {
        return set(key, value, Types.FLOAT);
    }

    default T set(String key, Double value) {
        return set(key, value, Types.DOUBLE);
    }

    default T set(String key, BigDecimal value) {
        return set(key, value, Types.DECIMAL);
    }

    default T set(String key, Boolean value) {
        return set(key, value, Types.TINYINT);
    }

    default T set(String key, java.util.Date value) {
        return set(key, value, Types.DATE);
    }

    default T set(String key, AutoCalculate value) {
        return set(key, value, Types.OTHER);
    }

    default T set(Map<String, ? extends Serializable> columns) {
        if (!StringUtils.isEmpty(columns)) {
            columns.forEach((k, v) -> this.set(k, v, Types.OTHER));
        }
        return (T) this;
    }

}
