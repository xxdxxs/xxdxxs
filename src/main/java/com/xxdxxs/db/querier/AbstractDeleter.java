package com.xxdxxs.db.querier;

import java.util.function.Supplier;

public abstract class AbstractDeleter<R extends AbstractDeleter<R, F>, F extends Criteria<F, F>> extends AbstractRestriction<R, F> {

    public String table;

    protected AbstractDeleter(Supplier<F> restricter) {
        super(restricter);
    }


    public R from(String table){
        this.table = table;
        return (R)this;
    }


    public String getTable(){
        return this.table;
    }
}
