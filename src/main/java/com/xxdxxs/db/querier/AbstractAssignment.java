package com.xxdxxs.db.querier;

public abstract class AbstractAssignment<T extends AbstractAssignment<T>> implements Comparable<T>{

    protected String name;

    protected Object value;

    public AbstractAssignment() {
    }

    public AbstractAssignment(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int compareTo(T variable) {
        return name.compareTo(variable.getName());
    }
}
