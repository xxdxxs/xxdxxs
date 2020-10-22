package com.xxdxxs.db.jdbc;

import com.xxdxxs.enums.Operator;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author xxdxxs
 */
public class Where<T> {

    private T t;
    private List<Criteria> criteriaList = new ArrayList<>();

    private Queue<String> linkQueue = new LinkedList<String>();

    private Queue<String> aliasQueue = new LinkedList<String>();

    private boolean isMultipleTable = false;

    public Where(T t) {
        this.t = t;
    }

    public Where(T t, boolean isMultipleTable){
        this.t = t;
        this.isMultipleTable = isMultipleTable;
    }

    /**
     * 多表联合查询时，设置查询字段属于哪张表
     * @param tableName
     * @return
     */
    public Where ofWhich(String tableName){
        aliasQueue.offer(tableName);
        return this;
    };


    public Where whereEqual(String column, Object value){
        criteriaList.add(new Criteria(column, value, Operator.EQUAL));
        return this;
    }

    public Where whereIn(String column, List<?> list){
        criteriaList.add(new Criteria(column, list, Operator.IN));
        return this;
    }

    public Where whereBetween(String column, Object left, Object right){
        Pair pair = Pair.of(left, right);
        criteriaList.add(new Criteria(column, pair, Operator.BETWEEN));
        return this;
    }

    public Where whereLessThan(String column, Object value){
        return this;
    }

    public Where whereMoreThan(String column, Object value){
        return this;
    }

    public Where whereLessEqual(String column, Object value){
        return this;
    }

    public Where whereMoreEqual(String column, Object value){
        return this;
    }

    public Where whereLike(String column, Object value){
        return this;
    }


    public Where or(){
        linkQueue.offer(" or ");
        return this;
    }

    public Where and(){
        linkQueue.offer(" and ");
        return this;
    }

    public T end(){
        return t;
    }

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
        criteriaList.stream().forEach( x -> {
            stringBuffer.append(x.toString()).append(linkQueue.poll());
        });
        return stringBuffer.toString();
    }
}
