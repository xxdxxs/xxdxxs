package com.xxdxxs.db.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author xxdxxs
 */
public abstract class BaseDao<E> {

    private JdbcTemplate jdbcTemplate;

    public void setTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<? extends E> find(){
        return null;
    }

    public List<? extends E> find(Select select){
        return null;
    }

    public List<? extends E> find(DataFilter dataFilter){
        return null;
    }

    public List<? extends E> findOne(DataFilter dataFilter){
        return null;
    }

    public int update(){
        return 1;
    }

    public int update(DataFilter dataFilter){
        return 1;
    }

    public int delete(){
        return 1;
    }

    public Boolean insert(E entity){
        return null;
    }

}
