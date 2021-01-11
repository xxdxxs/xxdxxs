package com.xxdxxs.service;

import com.xxdxxs.db.querier.Pagination;

import java.util.*;

/**
 * 条件过滤类
 *
 * @author xxdxxs
 */
public abstract class ConditionFilterSupport<T extends ConditionFilterSupport> {

    private Pagination<?> pager;

    private Map<String, Boolean> sorts = new LinkedHashMap<>();

    private Set<String> groups = new LinkedHashSet();

    public T groupBy(String... columns) {
        groups.addAll(Arrays.asList(columns));
        return (T) this;
    }

    public T sort(String column) {
        return sort(column, true);
    }

    public T sort(String column, boolean isDesc) {
        sorts.put(column, isDesc);
        return (T) this;
    }

    public T limit(int page, int limit) {
        this.pager = new Pagination<>(page, limit);
        return (T) this;
    }

    public Map<String, Boolean> getSorts() {
        return sorts;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public String getGroupsAsString() {
        return String.join(",", groups);
    }

    public Pagination getPagination() {
        return pager;
    }
}
