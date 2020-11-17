package com.xxdxxs.db.jdbc;

import lombok.Data;

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
    private String primaryKey;

    /**
     * 查询时需要的字段
     */
    private String needColumns;

    /**
     * 对应的实体类
     */
    private Class clazz;


    public Table(){}

    public Table(Class clazz) {
        this.clazz = clazz;
    }

    public void ofClass(Class clazz){
        setClazz(clazz);
    }




}
