package com.xxdxxs.exception;

/**
 * @author xxdxxs
 */
public abstract class BaseException extends RuntimeException {

    public String code;

    public String msg;

    public BaseException() {
        super();
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
