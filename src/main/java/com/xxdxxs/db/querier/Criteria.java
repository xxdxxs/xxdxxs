package com.xxdxxs.db.querier;

import com.xxdxxs.support.NestWhere;
import com.xxdxxs.enums.Operator;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public interface Criteria<T, A> {
    T where(Criterion Criterion);

    default T where(Consumer<NestWhere> callback) {
        NestWhere nestWhere = nestWhere();
        callback.accept(nestWhere);
        return where(new Criterion(nestWhere));
    }

    default T where(String column, Object value) {
        if (value instanceof Collection) {
            return whereIn(column, (Collection<? extends Serializable>) value);
        }
        return whereEqual(column, value);
    }

    default T whereEqual(String column, Object value) {
        return where(new Criterion(column, value, Operator.EQUAL));
    }

    default T whereNotEqual(String column, Object value) {
        return where(new Criterion(column, value, Operator.NOT_EQUAL));
    }

    default T whereIn(String column, Collection<? extends Serializable> values) {
        return where(new Criterion(column, values, Operator.IN));
    }

    default T whereIn(String variable, Serializable... values) {
        return whereIn(variable, Arrays.asList(values));
    }

    default T whereNotIn(String column, Collection<? extends Serializable> values) {
        return where(new Criterion(column, values, Operator.NOT_IN));
    }

    default T whereNotIn(String variable, Serializable... values) {
        return whereNotIn(variable, Arrays.asList(values));
    }

    default T whereBetween(String column, Object left, Object right) {
        Pair pair = Pair.of(left, right);
        return where(new Criterion(column, pair, Operator.BETWEEN));
    }

    default T whereLessThan(String column, Object value) {
        return where(new Criterion(column, value, Operator.LESS_THAN));
    }

    default T whereGreaterThan(String column, Object value) {
        return where(new Criterion(column, value, Operator.GREATER_THAN));
    }

    default T whereLessEqual(String column, Object value) {
        return where(new Criterion(column, value, Operator.LESS_THAN_OR_EQUAL));
    }

    default T whereGreaterEqual(String column, Object value) {
        return where(new Criterion(column, value, Operator.GREATER_THAN_OR_EQUAL));
    }

    default T whereLike(String column, Object value) {
        return where(new Criterion(column, value, Operator.LIKE));
    }

    default T whereStartWith(String column, Object value) {
        return where(new Criterion(column, value, Operator.START_WITH));
    }

    default T whereEndWith(String column, Object value) {
        return where(new Criterion(column, value, Operator.END_WITH));
    }

    A where(boolean create);

    NestWhere nestWhere();

    T or();

    T and();

}
