package com.xxdxxs.db.querier;

import com.xxdxxs.db.jdbc.Column;

import java.util.*;
import java.util.function.Supplier;

public abstract class AbstractUpdater<R extends AbstractUpdater<R, F, V>,  F extends Criteria<F, F>, V extends Column>
        extends AbstractRestriction<R, F> implements Setter<R, V>{

    protected String table;

    protected final Set<V> assignments = new TreeSet<>();

    protected AbstractUpdater(Supplier<F> restricter) {
        super(restricter);
    }

    public R from(String tableName){
        this.table = tableName;
        return (R) this;
    }


    public String getTable(){
        return this.table;
    }


    @Override
    public R set(V variable) {
        assignments.add(variable);
        return (R)this;
    }


    @Override
    public Set<V> getSets() {
        return assignments;
    }


    public Integer[] getTypes(){
        List<Integer> list = new ArrayList<>();
        assignments.stream().forEach(k ->{
            list.add(k.getType());
        });
        Integer[] array = list.toArray(new Integer[assignments.size()]);
        return array;
    }



}
