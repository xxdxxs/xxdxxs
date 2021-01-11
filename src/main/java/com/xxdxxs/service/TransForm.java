package com.xxdxxs.service;

import com.xxdxxs.db.querier.Pagination;
import com.xxdxxs.enums.Operator;
import com.xxdxxs.support.Select;
import com.xxdxxs.utils.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class TransForm {

    public TransForm() {

    }

    public static Turn of(ConditionFilter conditionFilter) {
        return new Turn(conditionFilter);
    }

    public static Turn of(FormHandler formHandler) {
        return new Turn(formHandler);
    }


    public static class Turn {

        private ConditionFilter conditionFilter;

        private FormHandler formHandler;

        public Turn(ConditionFilter conditionFilter) {
            if (conditionFilter != null) {
                this.conditionFilter = conditionFilter;
            }
        }

        public Turn(FormHandler formHandler) {
            if (formHandler != null) {
                this.formHandler = formHandler;
            }
        }

        public static Turn of(ConditionFilter conditionFilter) {
            return new Turn(conditionFilter);
        }

        public static Turn of(FormHandler formHandler) {
            return new Turn(formHandler);
        }

        public Select getQuerier(String table) {
            Select select = Select.of().from(table);
            String columns = conditionFilter.getNeedColumnsAsString();
            if (!StringUtils.isEmpty(columns)) {
                select.columns(columns);
            }
            Map<String, ConditionFilter.Data> datas = conditionFilter.getData();
            datas.forEach((k, v) -> {
                String name = v.getName();
                Map<Operator, Object> values = v.getValues();
                values.forEach((j, g) -> {
                    Object value = g;
                    if (g instanceof Optional) {
                        value = ((Optional) g).get();
                    }
                    switch (j) {
                        default:
                        case EQUAL:
                            callBack(name, value, select::whereEqual);
                            break;
                        case NOT_EQUAL:
                            callBack(name, value, select::whereNotEqual);
                            break;
                        case LIKE:
                            callBack(name, value, select::whereLike);
                            break;
                        case START_WITH:
                            callBack(name, value, select::whereStartWith);
                            break;
                        case END_WITH:
                            callBack(name, value, select::whereEndWith);
                            break;
                        case IN:
                            callBack(name, (Collection<? extends Serializable>) g, select::whereIn);
                            break;
                        case LESS_THAN:
                            callBack(name, value, select::whereLessThan);
                            break;
                        case LESS_THAN_OR_EQUAL:
                            callBack(name, value, select::whereLessEqual);
                            break;
                        case GREATER_THAN:
                            callBack(name, value, select::whereGreaterThan);
                            break;
                        case GREATER_THAN_OR_EQUAL:
                            callBack(name, value, select::whereGreaterEqual);
                            break;
                    }
                });
            });
            String groups = conditionFilter.getGroupsAsString();
            if (!StringUtils.isEmpty(groups)) {
                select.groupBy(groups);
            }

            Map<String, Boolean> sorts = conditionFilter.getSorts();
            if (!sorts.isEmpty()) {
                sorts.forEach((k, v) -> {
                    select.sort(k, v);
                });
            }
            Pagination pager = conditionFilter.getPagination();
            if (pager != null) {
                select.limit(pager);
            }
            return select;
        }

        public <T> void callBack(String name, T object, BiConsumer<String, T> biConsumer) {
            biConsumer.accept(name, object);
        }
    }


}
