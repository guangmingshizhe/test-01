package com.study.config;

/**
 * Created by 12073 on 2020/3/13.
 */

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.ToString;

/**
 * 数据库连接bean
 *
 * @author limengyang
 * create  2019-03-01
 **/
@Data
@ToString
public class DataSourceBean {
    private String username;
    private String password;
    private String jdbcUrl;
    private String driverClassName;
    private String poolName;
    private Integer minimumIdle = 5;
    private Integer maximumPoolSize = 15;
    private Long idleTimeout = 120000L;
    private Integer connectionTimeout = 12000;
    private Long maxLifeTime = 600000L;
    private Integer leakDetectionThreshold = 30000;
    private Integer validationTimeout = 3000;
    private String connectTestQuery;

    public HikariDataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(this.getDriverClassName());
        hikariConfig.setJdbcUrl(this.getJdbcUrl());
        hikariConfig.setUsername(this.getUsername());
        hikariConfig.setPassword(this.getPassword());
        hikariConfig.setPoolName(this.getPoolName());
        hikariConfig.setMinimumIdle(this.getMinimumIdle());
        hikariConfig.setMaximumPoolSize(this.getMaximumPoolSize());
        hikariConfig.setIdleTimeout(this.getIdleTimeout());
        hikariConfig.setConnectionTimeout(this.getConnectionTimeout());
        hikariConfig.setMaxLifetime(this.getMaxLifeTime());
        hikariConfig.setLeakDetectionThreshold(this.getLeakDetectionThreshold());
        hikariConfig.setValidationTimeout(this.getValidationTimeout());
        if (this.getDriverClassName().toLowerCase().contains("oracle")) {
            connectTestQuery = "select 1 from dual";
        } else if (this.getDriverClassName().toLowerCase().contains("mysql")) {
            connectTestQuery = "select 1";
        }
        hikariConfig.setConnectionTestQuery(connectTestQuery);
        hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "1024");
        hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useUnicode", "true");
        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        return new HikariDataSource(hikariConfig);
    }
}
