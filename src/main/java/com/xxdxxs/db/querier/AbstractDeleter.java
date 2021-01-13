package com.xxdxxs.db.querier;

import java.util.function.Supplier;

/**
 * 抽象删除类
 * @author xxdxxs
 */
public abstract class AbstractDeleter<R extends AbstractDeleter<R, F>, F extends Criteria<F, F>> extends AbstractRestriction<R, F> {

    public R from(String tableName) {
        super.table.setTableName(tableName);
        return (R) this;
    }

    protected AbstractDeleter(Supplier<F> restricter) {
        super(restricter);
    }

}
