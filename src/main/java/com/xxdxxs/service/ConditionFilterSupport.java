package com.xxdxxs.service;

import java.util.*;

public abstract class ConditionFilterSupport<T extends ConditionFilterSupport> {

    private Map<String, Boolean> sorts = new LinkedHashMap<>();

    private Set groups = new LinkedHashSet();

    public T groupBy(String... columns){
        groups.addAll(Arrays.asList(columns));
        return (T)this;
    }

    public T sort(String column){
        return sort(column, true);
    }

    public T sort(String column, boolean isDesc){
        sorts.put(column, isDesc);
        return (T)this;
    }

    public Map<String, Boolean> getSorts() {
        return sorts;
    }

    public Set getGroups() {
        return groups;
    }

    public String getGroupsAsString() {
        return String.join(",", groups);
    }
}
