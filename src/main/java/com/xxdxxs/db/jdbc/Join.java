package com.xxdxxs.db.jdbc;


import com.xxdxxs.enums.Operator;
import com.xxdxxs.utils.CommonUtil;

public class Join {
    public final static String LEFT_JOIN = " left join ";
    public final static String RIGHT_JOIN = " right join ";

    private String baseTable;

    private String targetTable;

    private String type;

    private String baseColumn;

    private String targetColumn;


    public Join(String baseTable, String targetTable, String type, String baseColumn, String targetColumn) {
        this.baseTable = baseTable;
        this.targetTable = targetTable;
        this.baseColumn = baseColumn;
        this.type = type;
        this.targetColumn = targetColumn;
    }

    public String on(){
        if(!CommonUtil.isAllNotNull(baseTable, targetTable, baseColumn, targetColumn)){
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(type).append(targetTable).append(" on ")
                .append(baseTable + "."+ baseColumn).append(Operator.EQUAL.getSign())
                .append(targetTable + "." + targetColumn);
        return stringBuffer.toString();
    }


}
