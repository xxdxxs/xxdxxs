package com.xxdxxs.db.querier;

import com.xxdxxs.support.NestWhere;
import com.xxdxxs.enums.Operator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Consumer;

public interface Criteria<T, A> {
    T where(Criterion Criterion);

    default T where(Consumer<NestWhere> callback) {
        NestWhere nestWhere = nestWhere();
        callback.accept(nestWhere);
        return where(new Criterion(nestWhere));
    }

    default T where(String column, Object value){
        if(value instanceof List){
            return whereIn(column, (List<?>) value);
        }
        return whereEqual(column, value);
    }

    default T whereEqual(String column, Object value){
        return where(new Criterion(column, value, Operator.EQUAL));
    }

    default T whereIn(String column, List<?> list){
        return where(new Criterion(column, list, Operator.IN));
    }

    default T whereBetween(String column, Object left, Object right){
        Pair pair = Pair.of(left, right);
        return where(new Criterion(column, pair, Operator.IN));
    }

    default T whereLessThan(String column, Object value){
        return where(new Criterion(column, value, Operator.LESS_THAN));
    }

    default T whereMoreThan(String column, Object value){
        return where(new Criterion(column, value, Operator.GREATER_THAN));
    }

    default T whereLessEqual(String column, Object value){
        return where(new Criterion(column, value, Operator.LESS_THAN_OR_EQUAL));
    }

    default T whereMoreEqual(String column, Object value){
        return where(new Criterion(column, value, Operator.GREATER_THAN_OR_EQUAL));
    }

    default T whereLike(String column, Object value){
        return where(new Criterion(column, value, Operator.LIKE));
    }

    A where(boolean create);

    NestWhere nestWhere();

    T or();

    T and();

}
