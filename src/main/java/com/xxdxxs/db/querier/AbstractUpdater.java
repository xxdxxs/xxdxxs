package com.xxdxxs.db.querier;

import com.xxdxxs.support.Column;

import java.util.*;
import java.util.function.Supplier;

/**
 * 抽象更新类
 * @author xxdxxs
 */
public abstract class AbstractUpdater<R extends AbstractUpdater<R, F, V>, F extends Criteria<F, F>, V extends Column>
        extends AbstractRestriction<R, F> implements Setter<R, V> {

    protected final Set<V> assignments = new TreeSet<>();

    protected AbstractUpdater(Supplier<F> restricter) {
        super(restricter);
    }

    public R from(String tableName) {
        super.table.setTableName(tableName);
        return (R) this;
    }

    @Override
    public R set(V variable) {
        assignments.add(variable);
        return (R) this;
    }


    @Override
    public Set<V> getSets() {
        return assignments;
    }


    public int getColumnType(Object object) {
        return V.getType(object.getClass());
    }

}
