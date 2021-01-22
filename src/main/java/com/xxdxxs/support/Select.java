package com.xxdxxs.support;

import com.xxdxxs.db.querier.AbstractSelect;
import com.xxdxxs.db.querier.Join;
import com.xxdxxs.exception.JdbcException;
import com.xxdxxs.exception.enums.JdbcErrorMsg;
import com.xxdxxs.utils.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 查询
 *
 * @author xxdxxs
 */
public class Select extends AbstractSelect<Select, Where> implements FeaturedWhere<Select> {
    private Map<String, Join> joinMap = new LinkedHashMap();
    private final String BLANK_SPACE = " ";
    private Set<String> groups = new LinkedHashSet<>();
    private Map<String, Boolean> sorts = new LinkedHashMap<>();
    private boolean isNestSelect = false;
    private Select[] nestSelects = new Select[1];

    public Select() {
        super(Where::new);
        super.table = new Table();
    }

    public Select(Class targetClass) {
        super(Where::new);
        super.table = new Table(targetClass);
    }


    public static Select of() {
        return new Select();
    }

    public Select of(Class targetClass) {
        return new Select(targetClass);
    }

    public Select defaultClass(Class clazz) {
        this.table.setClazz(clazz);
        return this;
    }

    public Select from(String table, String alias) {
        super.table.setTableName(table);
        super.table.setAlias(alias);
        return this;
    }

    public Select from(Select nestSelect) {
        /**设置子查询**/
        setNestSelect(nestSelect);
        this.nestSelects[0] = nestSelect;
        return this;
    }

    public Select from(Supplier<Select> supplier) {
        /**设置子查询**/
        Select nestSelect = supplier.get();
        setNestSelect(nestSelect);
        this.nestSelects[0] = nestSelect;
        return this;
    }

    public Select columns(String columns) {
        this.table.setNeedColumns(StringUtils.stringToList(columns, ","));
        return this;
    }

    public Select columns(String... columns) {
        this.table.setNeedColumns(Arrays.asList(columns));
        return this;
    }


    public Select groupBy(String column) {
        this.groups.add(column);
        return this;
    }

    public Select sort(String column) {
        sort(column, true);
        return this;
    }

    public Select sort(String column, boolean isDesc) {
        this.sorts.put(column, isDesc);
        return this;
    }


    public Select joinLeft(String targetTable, String baseColumn, String targetColumn) {
        return join(targetTable, Join.LEFT_JOIN, baseColumn, targetColumn);
    }

    public Select joinRight(String targetTable, String baseColumn, String targetColumn) {
        return join(targetTable, Join.RIGHT_JOIN, baseColumn, targetColumn);
    }

    private Select join(String targetTable, String type, String baseColumn, String targetColumn) {
        Join join = new Join(targetTable, Join.LEFT_JOIN, baseColumn, targetColumn);
        joinMap.put(targetTable, join);
        return this;
    }

    public Count count() {
        return new Count(this, "1");
    }

    public Count count(String column) {
        return new Count(this, column);
    }


    public Map<String, Object> getParams() {
        return getRestriction().getParamValues();
    }

    public void setNestSelect(Select nestSelect){
        this.isNestSelect = true;
        nestSelect.getRestriction().ofNestSelect();
    }

    public boolean isNestSelect(){
        return isNestSelect;
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(" select ");
        List<String> needColumns = table.getNeedColumns();
        if (StringUtils.isEmpty(needColumns)) {
            stringBuffer.append(" * ");
        } else {
            stringBuffer.append(StringUtils.listToString(needColumns));
        }
        /**判断是否有子查询**/
        if (isNestSelect) {
            Select nestSelect = nestSelects[0];
            stringBuffer.append(" from ( ");
            stringBuffer.append(nestSelect.toString() + " ) " );
            if (StringUtils.isEmpty(nestSelect.getTable().getTableName())) {
                throw new JdbcException(JdbcErrorMsg.TABLENAME_IS_NULL);
            }
            stringBuffer.append(" nest" + nestSelect.getTable().getTableName());
            this.getParams().putAll(nestSelect.getParams());
        } else {
            stringBuffer.append(" from ").append(table.getTableName());
        }
        if (!StringUtils.isEmpty(table.getAlias())) {
            stringBuffer.append(BLANK_SPACE + table.getAlias());
        }
        if (!joinMap.isEmpty()) {
            joinMap.forEach((k, v) -> {
                stringBuffer.append(v.on());
            });
        }
        if (getRestriction().criterionList.size() > 0) {
            stringBuffer.append(" where ");
        }
        stringBuffer.append(getRestriction().toString());

        if (!groups.isEmpty()) {
            stringBuffer.append(" group by ");
            groups.stream().forEach(x -> {
                stringBuffer.append(x).append(", ");
            });
            stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        }

        if (!sorts.isEmpty()) {
            stringBuffer.append(" order by ");
            sorts.forEach((k, v) -> {
                stringBuffer.append(k).append(v ? " desc" : " asc").append(", ");
            });
            stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        }
        if (pager != null) {
            stringBuffer.append(pager.limit());
        }
        return stringBuffer.toString();
    }


    public class Count {
        private Select select;
        private String counting;

        public Count(Select select, String counting) {
            this.select = select;
            this.counting = counting;
        }

        public Select getSelect() {
            return this.select;
        }


        @Override
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(" select ")
                    .append(" count(").append(counting).append(")")
                    .append(" from ").append(table.getTableName());
            if (!StringUtils.isEmpty(select.table.getAlias())) {
                stringBuffer.append(BLANK_SPACE + table.getAlias());
            }
            if (!joinMap.isEmpty()) {
                joinMap.forEach((k, v) -> {
                    stringBuffer.append(v.on());
                });
            }
            if (select.getRestriction().criterionList.size() > 0) {
                stringBuffer.append(" where ");
            }
            stringBuffer.append(select.getRestriction().toString());
            return stringBuffer.toString();
        }

    }


}
