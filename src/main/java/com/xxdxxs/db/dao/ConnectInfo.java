package com.xxdxxs.db.dao;

import lombok.Data;

/**
 * 数据库连接信息
 * @author xxdxxs
 */
@Data
public class ConnectInfo {

    private final String url;

    private final String username;

    private final String password;

    private final String database;

    private final String driverClassName;

    private final Integer maxActive;

    private final Integer minIdle;

    private final Integer initialSize;

}
