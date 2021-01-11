package com.xxdxxs.support;

import lombok.Data;

import java.util.List;

/**
 * 表
 *
 * @author xxdxxs
 */
@Data
public class Table {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表别名
     */
    private String alias;

    /**
     * 主键
     */
    private PrimaryKey primaryKey;

    /**
     * 查询时需要的字段
     */
    private List<String> needColumns;

    /**
     * 对应的实体类
     */
    private Class clazz;


    public Table() {
    }

    public Table(Class clazz) {
        this.clazz = clazz;
    }


    public void ofClass(Class clazz) {
        setClazz(clazz);
    }


    public boolean hasPrimaryKey() {
        return primaryKey.exist();
    }

}
