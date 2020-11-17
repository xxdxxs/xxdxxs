package com.xxdxxs.db.jdbc;

import com.xxdxxs.db.querier.AbstractUpdater;
import com.xxdxxs.db.querier.Criterion;
import com.xxdxxs.db.querier.FeaturedSetter;

import java.util.*;


public class Update extends AbstractUpdater<Update, Where, Column> implements FeaturedSetter<Update>, FeaturedWhere<Update> {

    /**
     * 区分预编译sql是冒号还是问号形式
     */
    public final static String DEFINED = "DEFINED";

    public Update() {
        super(Where::new);
    }

    public static Update of(){
        return new Update();
    }


    public Map<String, Object> getParams(){
        Map<String, Object> map = new HashMap(assignments.size());
        assignments.stream().forEach(k ->{
            map.put(k.getName(), k.getValue());
        });
        getRestriction().getParamValues().forEach((k, v) ->{
            map.put(k, v);
        });
        return map;
    }

    public Object[] getArgs(){
        List<Object> list = new ArrayList<>();
        assignments.stream().forEach(k ->{
            list.add(k.getValue());
        });
        getRestriction().criterionList.stream().forEach(k ->{
            list.add(k.getValue());
        });
        Object[] array = list.toArray(new Object[assignments.size()]);
        return array;
    }

    @Override
    public String toString() {
        return  stringfy("");
    }

    public String stringfy(String type){
        StringBuffer stringBuffer = new StringBuffer(" update  ");
        stringBuffer.append(getTable());
        Set<Column> sets = getSets();
        if(sets.size() > 0){
            stringBuffer.append(" set ");
        }
        sets.stream().forEach(column -> {
            stringBuffer.append(column.getName() + " = :"+ column.getName());
            stringBuffer.append(",");
        });
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        List<Criterion> criterionList = getRestriction ().criterionList;
        if(criterionList.size() > 0){
            stringBuffer.append(" where ");
        }
        String str = stringBuffer.append(getRestriction().toString()).toString();
        if(DEFINED.equals(type)){
            for(Column column: sets){
                str = str.replaceAll(":"+column.getName(), "?");
            };
            for(Criterion criterion : criterionList){
                str = str.replaceAll(":"+criterion.getColumn(), "?");
            }
        }
        return str;
    }
}
