package com.xxdxxs.support;

import com.xxdxxs.db.querier.AbstractCreater;
import com.xxdxxs.db.querier.FeaturedSetter;

import java.util.Set;

/**
 * 新增
 *
 * @author xxdxxs
 */
public class Insert extends AbstractCreater<Insert, Column> implements FeaturedSetter<Insert> {


    private Insert() {
        super();
    }

    public static Insert of() {
        return new Insert();
    }

    @Override
    public String toString() {
        return stringfy("");
    }

    public String stringfy(String type) {
        StringBuffer stringBuffer = new StringBuffer(" insert into ");
        stringBuffer.append(super.getTable().getTableName());
        Set<Column> sets = getSets();
        if (sets.isEmpty()) {
            return "";
        }
        stringBuffer.append(" ( ");
        sets.stream().forEach(column -> {
            stringBuffer.append(column.getName());
            stringBuffer.append(",");
        });
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        stringBuffer.append(" ) values (");
        sets.stream().forEach(column -> {
            stringBuffer.append(":" + column.getName());
            stringBuffer.append(",");
        });
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        stringBuffer.append(" )");
        String str = stringBuffer.toString();
        if (Column.DEFINED.equals(type)) {
            for (Column column : sets) {
                str = str.replaceAll(":" + column.getName(), "?");
            }
        }
        return str;
    }
}
