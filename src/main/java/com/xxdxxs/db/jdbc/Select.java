package com.xxdxxs.db.jdbc;

import com.xxdxxs.db.querier.AbstractSelect;
import com.xxdxxs.db.querier.Join;
import org.springframework.util.StringUtils;
import java.util.*;

public class Select extends AbstractSelect<Select, Where> implements FeaturedWhere<Select> {
    private Table table;
    private Map<String, Join> joinMap = new LinkedHashMap();
    private final String BLANK_SPACE = " ";
    private List<String> groups = new ArrayList<>();
    private Map<String, Boolean> sorts = new LinkedHashMap<>();

    public Select() {
        super(Where::new);
        this.table = new Table();
    }

    public Select(Class targetClass) {
        super(Where::new);
        this.table = new Table(targetClass);
    }

    public static Select of(){
        return new Select();
    }

    public Select of(Class targetClass){
        return new Select(targetClass);
    }

    public Select defaultClass(Class clazz){
        this.table.setClazz(clazz);
        return this;
    }

    public Select from(String table){
        this.table.setTableName(table);
        return this;
    }

    public Select from(String table, String alias){
        this.table.setTableName(table);
        this.table.setAlias(alias);
        return this;
    }

    public Select columns(String columns){
        this.table.setNeedColumns(columns);
        return this;
    }

    public Select groupBy(String column){
        this.groups.add(column);
        return this;
    }

    public Select sort(String column){
        sort(column, true);
        return this;
    }

    public Select sort(String column, boolean isDesc){
        this.sorts.put(column, isDesc);
        return this;
    }


    public Select joinLeft(String targetTable, String baseColumn, String targetColumn){
        return join(targetTable, Join.LEFT_JOIN, baseColumn, targetColumn);
    }

    public Select joinRight(String targetTable, String baseColumn, String targetColumn){
        return join(targetTable, Join.RIGHT_JOIN, baseColumn, targetColumn);
    }

    private Select join(String targetTable, String type, String baseColumn, String targetColumn){
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


    public Map<String, Object> getParams(){
        return getRestriction().getParamValues();
    }

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer(" select ");
        String needColumns = table.getNeedColumns();
        if(StringUtils.isEmpty(needColumns)){
            stringBuffer.append(" * ");
        }else {
            stringBuffer.append(needColumns);
        }
        stringBuffer.append(" from ").append(table.getTableName());
        if(!StringUtils.isEmpty(table.getAlias())){
            stringBuffer.append(BLANK_SPACE + table.getAlias());
        }
        if( !joinMap.isEmpty() ){
            joinMap.forEach((k, v) ->{
                stringBuffer.append(v.on());
            });
        }
        if(getRestriction().criterionList.size() > 0){
            stringBuffer.append(" where ");
        }
        stringBuffer.append(getRestriction().toString());

        if(!groups.isEmpty()){
            stringBuffer.append(" group by ");
            groups.stream().forEach(x ->{
                stringBuffer.append(x).append(", ");
            });
            stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        }

        if(!sorts.isEmpty()){
            stringBuffer.append(" order by ");
            sorts.forEach((k, v) ->{
                stringBuffer.append(k).append(v ? " desc" : " asc").append(", ");
            });
            stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
        }
        if(pager != null){
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

        public Select getSelect(){
            return this.select;
        }


        @Override
        public String toString(){
            StringBuffer stringBuffer = new StringBuffer(" select ")
                    .append(" count(").append(counting).append(")")
                    .append(" from ").append(table.getTableName());
            if(!StringUtils.isEmpty(select.table.getAlias())){
                stringBuffer.append(BLANK_SPACE + table.getAlias());
            }
            if( !joinMap.isEmpty() ){
                joinMap.forEach((k, v) ->{
                    stringBuffer.append(v.on());
                });
            }
            if(select.getRestriction().criterionList.size() > 0){
                stringBuffer.append(" where ");
            }
            stringBuffer.append(select.getRestriction().toString());
            return stringBuffer.toString();
        }

    }

    public static void main(String[] args) {
      //  System.out.println(new Where<>(new Select()) instanceof NestWhere);
    }


}
