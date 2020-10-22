package com.xxdxxs.db.jdbc;

import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class Select extends Operate {

    private Table table;
    private Map<String, Join> joinMap = new LinkedHashMap();
    private Where where;

    public Select() {
        this.table = new Table();
    }

    public static Select of(){
        return new Select();
    }

    public Select defaultClass(Class clazz){
        this.table.setClazz(clazz);
        return this;
    }

    public Select from(String table){
        this.table.setTableName(table);
        return this;
    }

    public Select columns(String columns){
        this.table.setNeedColumns(columns);
        return this;
    }

    public Select joinLeft(String targetTable, String baseColumn, String targetColumn){
        return join(this.table.getTableName(), targetTable, Join.LEFT_JOIN, baseColumn, targetColumn);
    }

    public Select joinLeft(String baseTable, String targetTable, String baseColumn, String targetColumn){
        return join(baseTable, targetTable, Join.LEFT_JOIN, baseColumn, targetColumn);
    }


    public Select joinRight(String targetTable, String baseColumn, String targetColumn){
        return join(this.table.getTableName(), targetTable, Join.RIGHT_JOIN, baseColumn, targetColumn);
    }

    public Select joinRight(String baseTable, String targetTable, String baseColumn, String targetColumn){
        return join(baseTable, targetTable, Join.RIGHT_JOIN, baseColumn, targetColumn);
    }

    public Select join(String baseTable, String targetTable, String type, String baseColumn, String targetColumn){
        Join join = new Join(baseTable, targetTable, Join.LEFT_JOIN, baseColumn, targetColumn);
        joinMap.put(targetTable, join);
        return this;
    }

    public Where createCriteria(Select select){
        where = new Where(select);
        return where;
    }

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer(" select ");
        String needColumns = table.getNeedColumns();
        if(needColumns.isEmpty()){
            stringBuffer.append(" * ");
        }else {
            stringBuffer.append(needColumns);
        }
        stringBuffer.append(" from ").append(table.getTableName());
        if( !joinMap.isEmpty() ){
            joinMap.forEach((k, v) ->{
                stringBuffer.append(v.on());
            });
        }
        stringBuffer.append(where.toString());
        return stringBuffer.toString();
    }


    public static void main(String[] args) {
        Select select = Select.of();
     //   select.from("inve").columns("id, gco,gna").whereEqual("age", 23).and().whereBetween("time", "2020-10-23", "2020-10-24").;
        Pair pair = Pair.of("11", "22");
        //select()
    }

}
