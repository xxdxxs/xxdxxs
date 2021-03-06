package com.xxdxxs.db.querier;

import com.xxdxxs.support.NestWhere;
import com.xxdxxs.support.Table;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 抽象条件设置类
 * @author xxdxxs
 */
public abstract class AbstractRestriction<
        E extends AbstractRestriction<E, F>,
        F extends Criteria<F, F>> extends AbstractSpace<E> {

    protected final F restriction;

    protected Supplier<F> restricter;

    protected AbstractRestriction(Supplier<F> restricter) {
        super();
        this.restriction = restricter.get();
        this.restricter = restricter;
    }


    @SuppressWarnings("unchecked")
    public E where(Criterion criterion) {
        restriction.where(criterion);
        return (E) this;
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

    public F getRestriction() {
        return restriction;
    }

    public NestWhere nestWhere() {
        return new NestWhere();
    }

}
