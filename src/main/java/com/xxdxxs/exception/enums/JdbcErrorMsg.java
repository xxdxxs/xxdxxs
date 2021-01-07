package com.xxdxxs.exception.enums;

/**
 * @author xxdxxs
 */
public enum JdbcErrorMsg {
    TABLENAME_IS_NULL("10001", "tableName is required, not be null"),
    TABLENAME_IS_DIFFERENT("10002", "tableName is different from default"),
    OVER_LIMIT_COMMIT("10003", "num of update over limit"),
    NOT_EXIST_UNIQUE_COLUMN("10004", "entity is not exist unique column"),
    UNIQUE_COLUMN_IS_NULL("10005", "unique column can not be null"),
    DATA_BY_UNIQUE_COLUMN_NOT_EXIST("10006", "can not find data from table by unique column"),
    DATA_BY_UNIQUE_COLUMN_MORETHAN_ONE("10007", "find more than one data from table by unique column")
    ;
    private String code;
    private String msg;

    JdbcErrorMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
