package com.xxdxxs.db.jdbc;

import lombok.Data;

@Data
public class Table {

    private String tableName;

    private String primaryKey;

    private String needColumns;

    private Class clazz;




}
