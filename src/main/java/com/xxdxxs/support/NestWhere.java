package com.xxdxxs.support;


import com.xxdxxs.db.querier.Criterion;

/**
 * @author xxdxxs
 */
public class NestWhere extends Where  {

    @Override
    public String toString(){
        StringBuffer stringBuffer = new StringBuffer(" ( ");
        int i = 0;
        int j = criterionList.size();
        for (Criterion criterion : criterionList){
            stringBuffer.append(criterion.toString());
            if(i < j-1){
                stringBuffer.append(linkList.get(i) ? " and " : " or ");
            }
            i++;
        }
        stringBuffer .append(" ) ");
        return stringBuffer.toString();
    }
}
