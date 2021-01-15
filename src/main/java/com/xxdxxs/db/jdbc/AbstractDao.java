package com.xxdxxs.db.jdbc;


import com.xxdxxs.db.dao.Template;

/**
 * @author xxdxxs
 */
public abstract class AbstractDao<T extends Template> {

    protected T template;

    protected void setTemplate(T template) {
        this.template = template;
    }

    protected T getTemplate() {
        return template;
    }
}
