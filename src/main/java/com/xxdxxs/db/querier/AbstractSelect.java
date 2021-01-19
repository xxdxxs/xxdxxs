package com.xxdxxs.db.querier;

import com.xxdxxs.exception.JdbcException;
import com.xxdxxs.exception.enums.JdbcErrorMsg;
import com.xxdxxs.utils.StringUtils;

import java.util.function.Supplier;


/**
 * 抽象查询类
 * @author xxdxxs
 */
public abstract class AbstractSelect<R extends AbstractSelect<R, F>, F extends Criteria<F, F>> extends AbstractRestriction<R, F> {


    protected Pagination<?> pager;

    protected AbstractSelect(Supplier<F> restricter) {
        super(restricter);
    }


    public R from(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            throw new JdbcException(JdbcErrorMsg.TABLENAME_IS_NULL);
        }
        table.setTableName(tableName);
        return (R) this;
    }


    public R limit(int page, int limit) {
        if (limit < 0) {
            limit = 0;
        }
        if (page <= 0) {
            page = 1;
        }
        this.pager = new Pagination<>(page, limit);
        return (R) this;
    }


    public R limit(Pagination<?> pager) {
        if (pager.getSize() < 0) {
            pager.setSize(0);
        }
        if (pager.getPage() <= 0) {
            pager.setPage(1);
        }
        this.pager = pager;
        return (R) this;
    }

    public R limit(int limit) {
        return limit(0, limit);
    }

}
