package com.xxdxxs.db.querier;

import com.xxdxxs.support.Column;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Set;

/**
 * 字段值设置接口
 *
 * @author xxdxxs
 */
public interface Setter<R, V extends Column> {

    R set(V variable);

    default R set(Collection<V> variables) {
        if (!ObjectUtils.isEmpty(variables)) {
            variables.forEach(this::set);
        }

        return (R) this;
    }

    Set<V> getSets();
}
