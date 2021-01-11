package com.xxdxxs.db.querier;

import com.xxdxxs.support.Table;

/**
 * @author xxdxxs
 */
public abstract class AbstractSpace<E extends AbstractSpace> {

    protected Table table;

    public AbstractSpace() {
        this.table = new Table();
    }

    public Table getTable() {
        return this.table;
    }

}
