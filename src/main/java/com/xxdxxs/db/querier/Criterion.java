package com.xxdxxs.db.querier;

import com.xxdxxs.support.Where;
import com.xxdxxs.enums.Operator;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 条件设置处理类
 * @author xxdxxs
 */
public class Criterion {

    private String alias;

    private String column;

    private Object value;

    private Operator operator;

    private Map<String, Object> paramMap = new LinkedHashMap<>();

    private boolean isNull = false;

    private boolean isPairable = false;

    private boolean isList = false;

    private boolean isUseAlias = false;

    public Criterion(Where where) {
        initialize(null, where, null);
    }

    public Criterion(String column, Object value, Operator operator) {
        initialize(column, value, operator);
    }

    public Criterion(String column, Pair pair, Operator operator) {
        initialize(column, pair, operator);
        this.isPairable = true;
    }

    public Criterion(String column, Collection<? extends Serializable> collection , Operator operator) {
        initialize(column, collection, operator);
        this.isList = true;
    }

    private void initialize(String column, Object value, Operator operator) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    private String buildCriteria(){
        StringBuffer builder = new StringBuffer();
        if(this.column == null){
            return "";
        }
        String mark = this.column.replaceAll("\\.", "");
        if(isPairable){
            builder.append(column).append(Operator.GREATER_THAN_OR_EQUAL.getSign()).append(" :left" + mark).append(" and ")
                    .append(column).append(Operator.LESS_THAN_OR_EQUAL.getSign()).append(" :right" + mark);
            paramMap.put("left" + mark, ((Pair)this.value).getLeft());
            paramMap.put("right" + mark, ((Pair)this.value).getRight());
        }
        else if(isList){
            builder.append(column).append(operator.getSign()).append(" (:" + mark + ") ");
            paramMap.put(mark, value);
        }else{
            builder.append(column).append(operator.getSign()).append(" :" + mark);
            paramMap.put(mark, value);
        }
        return builder.toString();
    }

    public Map<String, Object> paramValues(){
        return this.paramMap;
    }


    @Override
    public String toString(){
        return buildCriteria();
    }

    public static void main(String[] args) {

    }
}
