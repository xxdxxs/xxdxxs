package com.xxdxxs.db.querier;


import com.xxdxxs.enums.Operator;
import com.xxdxxs.utils.CommonUtils;
import com.xxdxxs.utils.StringUtils;

/**
 * 过滤条件连接类
 *
 * @author xxdxxs
 */
public class Join {
    public final static String LEFT_JOIN = " left join ";
    public final static String RIGHT_JOIN = " right join ";

    private String targetTable;

    private String type;

    private String baseColumn;

    private String targetColumn;


    public Join(String targetTable, String type, String baseColumn, String targetColumn) {
        this.targetTable = targetTable;
        this.baseColumn = baseColumn;
        this.type = type;
        this.targetColumn = targetColumn;
    }

    public String on() {
        if (!StringUtils.isAllNotEmpty(targetTable, baseColumn, targetColumn)) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(type).append(targetTable).append(" on ")
                .append(baseColumn).append(Operator.EQUAL.getSign())
                .append(targetColumn);
        return stringBuffer.toString();
    }

}
