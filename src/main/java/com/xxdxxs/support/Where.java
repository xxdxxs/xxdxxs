package com.xxdxxs.support;

import com.xxdxxs.db.querier.Criterion;

import java.util.*;

/**
 * 筛选条件
 * @author xxdxxs
 */
public class Where implements FeaturedWhere<Where> {


    private final String WHERE = " where ";

    protected List<Criterion> criterionList = new ArrayList<>();

    protected List<Boolean> linkList = new ArrayList<>();

    protected Map<String, Object> paramValues = new LinkedHashMap<>();

    private boolean isJoinByAnd = true;

    private boolean isOfNestSelect = false;

    public Map<String, Object> getParamValues() {
        criterionList.stream().forEach(x -> {
            paramValues.putAll(x.paramValues());
        });
        return paramValues;
    }

    public Where() {

    }

    protected void setParam(Map<String, Object> map) {
        paramValues.putAll(map);
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        int j = criterionList.size();
        for (Criterion criterion : criterionList) {
            if (isOfNestSelect) {
                criterion.ofNestSelect();
            }
            if (criterion.getValue().getClass() == NestWhere.class) {
                NestWhere childWhere = (NestWhere) criterion.getValue();
                stringBuffer.append(childWhere.toString());
                setParam(childWhere.getParamValues());
            }
            stringBuffer.append(criterion.toString());
            if (i < j - 1) {
                stringBuffer.append(linkList.get(i) ? " and " : " or ");
            }
            setParam(criterion.paramValues());
            i++;
        }
        return stringBuffer.toString();
    }

    public void appendCriterion(StringBuffer stringBuffer, Criterion criterion, int index) {
        String link = " ";
        try {
            boolean flag = linkList.get(index);
            if (flag) {
                link = " and ";
            } else {
                link = " or  ";
            }
        } catch (NullPointerException e) {
            link = " ";
        }
        stringBuffer.append(criterion.toString()).append(link);
    }

    @Override
    public Where where(Criterion criterion) {
        if (isOfNestSelect) {
            criterion.ofNestSelect();
        }
        criterionList.add(criterion);
        if (criterionList.size() > 1) {
            linkList.add(isJoinByAnd);
        }
        return this;
    }

    @Override
    public Where where(boolean create) {
        return create ? new Where() : this;
    }

    @Override
    public NestWhere nestWhere() {
        return new NestWhere();
    }

    public List<Criterion> getCriterions() {
        return criterionList;
    }

    public List<Boolean> getlinkList() {
        return linkList;
    }


    @Override
    public Where or() {
        isJoinByAnd = false;
        return this;
    }

    @Override
    public Where and() {
        isJoinByAnd = true;
        return this;
    }

    public void ofNestSelect(){
        this.isOfNestSelect = true;
    }
}
