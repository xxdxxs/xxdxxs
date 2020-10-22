package com.xxdxxs.db.jdbc;

import com.xxdxxs.enums.Operator;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class Criteria {

    private String column;

    private Object value;

    private Operator operator;

    private boolean isNull = false;

    private boolean isPairable = false;

    private boolean isList = false;

    public Criteria(String column, Object value, Operator operator) {
        initialize(column, value, operator);
    }

    public Criteria(String column, Pair pair, Operator operator) {
        initialize(column, pair, operator);
        this.isPairable = true;
    }

    public Criteria(String column, List<?> list , Operator operator) {
        initialize(column, list, operator);
        this.isList = true;
    }

    private void initialize(String column, Object value, Operator operator) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    private String buildCriteria(){
        StringBuffer builder = new StringBuffer();
        if(isPairable){
            builder.append(column).append(Operator.GREATER_THAN_OR_EQUAL.getSign()).append(" :"+column).append(" and ")
                    .append(column).append(Operator.LESS_THAN_OR_EQUAL.getSign()).append(" :"+column);
        }
        else if(isList){
            builder.append(column).append(operator.getSign()).append(" ( :" + column + ")");
        }else{
            builder.append(column).append(" ").append(operator.getSign()).append(" :"+column);
        }
        return builder.toString();
    }

    @Override
    public String toString(){
        return buildCriteria();
    }

}
