package com.xxdxxs.service;

import com.xxdxxs.db.querier.Pagination;
import com.xxdxxs.enums.Operator;
import com.xxdxxs.support.Select;
import com.xxdxxs.utils.StringUtils;
import com.xxdxxs.validation.Validation;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class TransForm {

    public TransForm(){

    }

    public static Turn of(ConditionFilter conditionFilter){
        return new Turn(conditionFilter);
    }

    public static Turn of(FormHandler formHandler){
        return new Turn(formHandler);
    }


    public static class Turn{

        private ConditionFilter conditionFilter;

        private FormHandler formHandler;

        public Turn(ConditionFilter conditionFilter){
            if(conditionFilter != null){
                this.conditionFilter = conditionFilter;
            }
        }

        public Turn(FormHandler formHandler){
            if(formHandler != null){
                this.formHandler = formHandler;
            }
        }

        public static Turn of(ConditionFilter conditionFilter) {
            return new Turn(conditionFilter);
        }

        public static Turn of(FormHandler formHandler) {
            return new Turn(formHandler);
        }

        public Select getQuerier(String table){
            Select select = Select.of().from(table);
            String columns = conditionFilter.getNeedColumnsAsString();
            if(!StringUtils.isEmpty(columns)){
                select.columns(columns);
            }
            Map<String, ConditionFilter.Data> datas = conditionFilter.getData();
            datas.forEach((k, v) -> {
                String name = v.getName();
                Map<Operator, Object> values = v.getValues();
                values.forEach((j, g) -> {
                    Object value = g;
                    if(g instanceof Optional){
                        value = ((Optional)g).get();
                        System.out.println("value= " + value);
                    }
                    switch (j){
                        default:
                        case EQUAL:
                            callBack(name, value, select::whereEqual);
                            break;
                        case NOT_EQUAL:
                            select.whereNotEqual(name, value);
                            break;
                        case LIKE:
                            select.whereLike(name, value);
                            break;
                        case START_WITH:
                            select.whereStartWith(name, value);
                            break;
                        case END_WITH:
                            select.whereEndWith(name, value);
                            break;
                        case IN:
                            select.whereIn(name, (Collection<? extends Serializable>) g);
                            break;
                        case LESS_THAN:
                            select.whereLessThan(name, g);
                            break;
                        case LESS_THAN_OR_EQUAL:
                            select.whereLessEqual(name, g);
                            break;
                        case GREATER_THAN:
                            select.whereMoreThan(name, g);
                            break;
                        case GREATER_THAN_OR_EQUAL:
                            select.whereMoreEqual(name, g);
                            break;
                    }
                });
            });
            String groups = conditionFilter.getGroupsAsString();
            if(!StringUtils.isEmpty(groups)){
                select.groupBy(groups);
            }

            Map<String, Boolean> sorts = conditionFilter.getSorts();
            if(!sorts.isEmpty()){
                sorts.forEach((k,v) -> {
                    select.sort(k, v);
                });
            }
            Pagination pager = conditionFilter.getPagination();
            if(pager != null) {
                select.limit(pager);
            }
            return select;
        }

        public void callBack (String name, Object object, BiConsumer<String, Object> biConsumer){
            biConsumer.accept(name, object);
        }
    }


    public static void main(String[] args) {
        Optional<String> a = Optional.of("sad");
        System.out.println(a.get());
    }

}
