package com.xxdxxs.enums;

public enum ValidatorEnum {
    PARAM_IS_NULL("100001", "参数为空"),
    PARAM_IS_VAILD("100002", "参数无效"),
    ERROR_FORMAT("100003", "参数格式不正确"),
    OUt_LIMIT("100004", "超出范围"),
    ERROE_GET_EXCEPTION("100005", "获取异常信息出错");

    private String code;

    private String msg;

    ValidatorEnum(String code, String msg) {
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


