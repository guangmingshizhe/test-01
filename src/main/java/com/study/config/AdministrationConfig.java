package com.study.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by 12073 on 2020/3/13.
 */
@Configuration
@MapperScan(basePackages = "com.study.mapper.administration", sqlSessionFactoryRef = "secondarySqlSessionFactory")
public class AdministrationConfig {
    /**
     * 连接池属性
     */
    @Bean
    @ConfigurationProperties(prefix = "hikari.secondary")
    public DataSourceBean secondaryDataSourceBean() {
        return new DataSourceBean();
    }


    /**
     * 连接池
     */
    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource() {
        DataSourceBean dataSource = secondaryDataSourceBean();
        return dataSource.getDataSource();
    }


    /**
     * Mybatis sqlSession
     */
    @Bean(name = "secondarySqlSessionFactory")
    @ConditionalOnBean(name = {"secondaryDataSource"})
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/administration/*.xml"));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        bean.getObject().getConfiguration().setCallSettersOnNulls(true);
        return bean.getObject();
    }


    /**
     * mysql-tehran数据库设置
     */
    @Bean(name = "secondaryTransactionManager")
    @ConditionalOnBean(name = {"secondaryDataSource"})
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
