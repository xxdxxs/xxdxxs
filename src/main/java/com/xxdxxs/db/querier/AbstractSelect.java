package com.xxdxxs.db.querier;

import java.util.function.Supplier;

public abstract class AbstractSelect<R extends AbstractSelect<R, F>, F extends Criteria<F, F>> extends AbstractRestriction<R, F>{


    protected Pagination<?> pager;

    protected AbstractSelect(Supplier<F> restricter) {
        super(restricter);
    }

    public R limit(int page, int limit){
        if (limit < 0) {
            limit = 0;
        }
        if (page <= 0) {
            page = 1;
        }
        this.pager = new Pagination<>(page, limit);
        return (R) this;
    }


    public R limit(int limit){
        return limit(0, limit);
    }

}
