package com.xxdxxs.db.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxdxxs.db.dao.ConnectInfo;
import com.xxdxxs.db.dao.Template;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import javax.sql.DataSource;
import java.sql.JDBCType;

/**
 * @author xxdxxs
 */
public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate implements Template {

    public JdbcTemplate() {
    }

    public static JdbcTemplate of(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    public static JdbcTemplate of(ConnectInfo connectInfo) {
        Assert.notNull(connectInfo, "connectInfo must not be null");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(connectInfo.getDriverClassName());
        dataSource.setUrl(connectInfo.getUrl());
        dataSource.setUsername(connectInfo.getUsername());
        dataSource.setPassword(connectInfo.getPassword());
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(600000);
        dataSource.setMaxActive(connectInfo.getMaxActive() == null? 20 : connectInfo.getMaxActive());
        dataSource.setMinIdle(connectInfo.getMinIdle() == null? 1 : connectInfo.getMinIdle());
        dataSource.setInitialSize(connectInfo.getInitialSize() == null? 1 : connectInfo.getInitialSize());
        return of(dataSource);
    }


    public static JdbcTemplate of(org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
        DataSource dataSource = jdbcTemplate.getDataSource();
        return of(dataSource);
    }

}
