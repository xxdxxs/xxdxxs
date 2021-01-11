package com.xxdxxs.service;

import com.xxdxxs.enums.Operator;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 条件过滤类
 *
 * @author xxdxxs
 */
public class ConditionFilter extends ConditionFilterSupport<ConditionFilter> {

    /**
     * 查询条件
     */
    private Map<String, Data> data = new LinkedHashMap<>();

    private List<String> needColumns = new ArrayList<>();

    public ConditionFilter() {
    }

    public static ConditionFilter of() {
        return new ConditionFilter();
    }

    public ConditionFilter set(String name, Operator operator, Object value) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name");
        }
        if (value != null || operator.equals(Operator.IS_NULL) || operator.equals(Operator.IS_NOT_NULL)) {
            this.data.computeIfAbsent(name, Data::new).setOperation(operator, value);
        }
        return this;
    }

    public Map<String, Data> getData() {
        return data;
    }

    public ConditionFilter setNeedColumns(List<String> columns) {
        this.needColumns = columns;
        return this;
    }

    public ConditionFilter setNeedColumns(String... columns) {
        this.needColumns = Arrays.asList(columns);
        return this;
    }

    public List<String> getNeedColumns() {
        return needColumns;
    }

    public String getNeedColumnsAsString() {
        return String.join(",", needColumns);
    }

    public static void main(String[] args) {
        ConditionFilter filter = ConditionFilter.of();
        filter.setNeedColumns("asd", "dasd", "rrre");
        System.out.println(filter.getNeedColumnsAsString());
    }

    /**
     * 等于
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter equal(String name, Serializable value) {
        return set(name, Operator.EQUAL, value);
    }

    /**
     * 不等于
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter notEqual(String name, Serializable value) {
        return set(name, Operator.NOT_EQUAL, value);
    }

    /**
     * 模糊查询
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter like(String name, Serializable value) {
        return set(name, Operator.LIKE, value);
    }

    /**
     * 以...开头模糊查询
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter startWith(String name, Serializable value) {
        return set(name, Operator.START_WITH, value);
    }

    /**
     * 以...结尾模糊查询
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter endWith(String name, Serializable value) {
        return set(name, Operator.END_WITH, value);
    }

    /**
     * 大于
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter greaterThan(String name, Serializable value) {
        return set(name, Operator.GREATER_THAN, value);
    }

    /**
     * 大于等于
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter greaterThanOrEqual(String name, Serializable value) {
        return set(name, Operator.GREATER_THAN_OR_EQUAL, value);
    }

    /**
     * 小于
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter lessThan(String name, Serializable value) {
        return set(name, Operator.LESS_THAN, value);
    }

    /**
     * 小于等于
     *
     * @param name
     * @param value
     * @return
     */
    public ConditionFilter lessThanOrEqual(String name, Serializable value) {
        return set(name, Operator.LESS_THAN_OR_EQUAL, value);
    }

    /**
     * 在范围内
     *
     * @param name
     * @param values
     * @return
     */
    public ConditionFilter in(String name, Collection<? extends Serializable> values) {
        return set(name, Operator.IN, values);
    }

    /**
     * 在范围内
     *
     * @param name
     * @param values
     * @return
     */
    public ConditionFilter in(String name, Serializable... values) {
        return set(name, Operator.IN, Arrays.asList(values));
    }


    public static class Data implements Serializable {
        private String name;

        private Map<Operator, Object> values = new EnumMap<>(Operator.class);

        public Data(String name) {
            setName(name);
        }

        public Data(String name, Operator operator) {
            this(name, operator, null);
        }

        public Data(String name, Operator operator, Object value) {
            setName(name);
            setOperation(operator, value);
        }

        public void setOperation(Operator operator, Object value) {
            this.values.put(operator, value);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<Operator, Object> getValues() {
            return values;
        }
    }

}
