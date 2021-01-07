package com.xxdxxs.exception;

import com.xxdxxs.exception.enums.JdbcErrorMsg;

/**
 * @author xxdxxs
 */
public class JdbcException extends DalException {

    public JdbcException(){
        super();
    }

    public JdbcException(String msg) {
        super(msg);
    }

    public JdbcException(JdbcErrorMsg jdbcErrorMsg) {
        super(jdbcErrorMsg.getMsg());
    }

}
