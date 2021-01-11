package com.xxdxxs.support;

import com.xxdxxs.db.querier.AbstractDeleter;
import com.xxdxxs.db.querier.Criterion;

import java.util.List;
import java.util.Map;

/**
 * 删除
 *
 * @author xxdxxs
 */
public class Delete extends AbstractDeleter<Delete, Where> implements FeaturedWhere<Delete> {

    public Delete() {
        super(Where::new);
    }

    public static Delete of() {
        return new Delete();
    }


    public Map<String, Object> getParams() {
        return getRestriction().getParamValues();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(" delete from ");
        stringBuffer.append(super.getTable().getTableName());
        List<Criterion> criterionList = getRestriction().criterionList;
        if (criterionList.size() > 0) {
            stringBuffer.append(" where ");
        }
        stringBuffer.append(getRestriction().toString()).toString();
        return stringBuffer.toString();
    }

}
