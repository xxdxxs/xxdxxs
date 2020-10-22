package com.xxdxxs.db.jdbc;

public class SqlBuilder {

    private DataFilter dataFilter;

    public SqlBuilder(DataFilter dataFilter) {
        this.dataFilter = dataFilter;
    }

    public static SqlBuilder of(DataFilter dataFilter){
       return new SqlBuilder(dataFilter);
    }
}
