package com.xxdxxs.db.querier;

import com.xxdxxs.support.Column;

import java.util.*;

/**
 * @author xxdxxs
 */
public abstract class AbstractCreater<R extends AbstractCreater<R, V>, V extends Column> extends AbstractSpace<R> implements Setter<R, V> {


    protected final Set<V> assignments = new TreeSet<>();

    public R into(String tableName) {
        super.table.setTableName(tableName);
        return (R) this;
    }


    @SuppressWarnings("unchecked")
    @Override
    public R set(V variable) {
        if (variable != null) {
            assignments.add(variable);
        }
        return (R) this;
    }

    public Map<String, Object> getParams() {
        Map<String, Object> map = new HashMap(assignments.size());
        assignments.stream().forEach(k -> {
            map.put(k.getName(), k.getValue());
        });
        return map;
    }


    public Object[] getArgs() {
        List<Object> list = new ArrayList<>();
        assignments.stream().forEach(k -> {
            list.add(k.getValue());
        });
        Object[] array = list.toArray(new Object[assignments.size()]);
        return array;
    }


    public int[] getTypes() {
        List<Integer> list = new ArrayList<>();
        assignments.stream().forEach(k -> {
            list.add(k.getType());
        });
        Integer[] array = list.toArray(new Integer[assignments.size()]);
        int[] types = Arrays.stream(array).mapToInt(Integer::valueOf).toArray();
        return types;
    }

    @Override
    public Set<V> getSets() {
        return assignments;
    }
}
