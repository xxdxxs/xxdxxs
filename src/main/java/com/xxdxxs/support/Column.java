package com.xxdxxs.support;

import com.xxdxxs.db.querier.AbstractAssignment;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * 字段列
 *
 * @author xxdxxs
 */
public class Column extends AbstractAssignment<Column> {

    /**
     * 区分预编译sql是冒号还是问号形式
     */
    public final static String DEFINED = "DEFINED";

    private static final Map<Class<?>, Integer> TYPES = new HashMap<>();

    static {

        TYPES.put(String.class, Types.VARCHAR);
        TYPES.put(BigDecimal.class, Types.DECIMAL);
        TYPES.put(Boolean.class, Types.TINYINT);
        TYPES.put(Byte.class, Types.TINYINT);
        TYPES.put(Short.class, Types.SMALLINT);
        TYPES.put(Integer.class, Types.INTEGER);
        TYPES.put(Long.class, Types.BIGINT);
        TYPES.put(Float.class, Types.FLOAT);
        TYPES.put(Double.class, Types.DOUBLE);
        TYPES.put(Byte[].class, Types.VARBINARY);
        TYPES.put(Date.class, Types.DATE);
        TYPES.put(Time.class, Types.TIME);
        TYPES.put(Timestamp.class, Types.TIMESTAMP);
        TYPES.put(AutoCalculate.class, Types.OTHER);

    }

    protected int type;

    public Column() {
        super();
    }

    public Column(String name, Object value, int type) {
        super(name, value);
        this.type = type;
    }

    public Column(String name, Object value, Class<?> clazz) {
        this(name, value, getType(clazz));
    }

    public Column(String name, Object value) {
        this(name, value, 0);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
        this.type = 0;
    }

    public int getType() {
        return type;
    }

    public static int getType(Class<?> clazz) {
        return TYPES.getOrDefault(clazz, Types.OTHER);
    }


}
