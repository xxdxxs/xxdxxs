package com.xxdxxs.db.querier;

import com.xxdxxs.db.jdbc.NestWhere;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractRestriction <
        E extends AbstractRestriction<E, F>,
        F extends Criteria<F,F>>{

    protected final F restriction;

    protected Supplier<F> restricter;

    protected AbstractRestriction(Supplier<F> restricter) {
        this.restriction = restricter.get();
        this.restricter = restricter;
    }

    @SuppressWarnings("unchecked")
    public E where(Criterion criterion){
        restriction.where(criterion);
        return (E)this;
    }

    @SuppressWarnings("unchecked")
    public E and() {
        restriction.and();
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E or() {
        restriction.or();
        return (E) this;
    }

    public F where(boolean create) {
        return create ? restricter.get() : restriction;
    }

    public F getRestriction(){
        return restriction;
    }

    public NestWhere nestWhere() {
        return new NestWhere();
    }

}
