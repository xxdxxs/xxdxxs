package com.xxdxxs.support;

import com.xxdxxs.db.querier.AbstractUpdater;
import com.xxdxxs.db.querier.Criterion;
import com.xxdxxs.db.querier.FeaturedSetter;
import java.util.*;
import java.util.List;


public class Update extends AbstractUpdater<Update, Where, Column> implements FeaturedSetter<Update>, FeaturedWhere<Update> {


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


    public int[] getTypes(){
        List<Integer> list = new ArrayList<>();
        assignments.stream().forEach(k ->{
            list.add(k.getType());
        });
        getRestriction().criterionList.stream().forEach(k ->{
            list.add(getColumnType(k.getValue()));
        });
        Integer[] array = list.toArray(new Integer[assignments.size()]);
        int[] types = Arrays.stream(array).mapToInt(Integer::valueOf).toArray();
        return types;
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
        if(Column.DEFINED.equals(type)){
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
