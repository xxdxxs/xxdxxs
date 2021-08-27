package com.xxdxxs.exception;


/**
 * 操作数据库异常类
 * @author xxdxxs
 */
public abstract class DalException extends BaseException {

    public String code;

    public String msg;

    public DalException() {
        super();
    }

    public DalException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public DalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public DalException(Throwable cause) {
        super(cause);
    }

    public DalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
