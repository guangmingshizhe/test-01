package com.study.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by 12073 on 2020/3/13.
 */
@Configuration
@MapperScan(basePackages = "com.study.mapper.business", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class BusinessConfig {

    /**
     * 连接池属性
     */
    @Bean
    @ConfigurationProperties(prefix = "hikari.primary")
    public DataSourceBean primaryDataSourceBean() {
        return new DataSourceBean();
    }


    /**
     * 连接池
     */
    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource primaryDataSource() {
        DataSourceBean dataSource = primaryDataSourceBean();
        return dataSource.getDataSource();
    }


    /**
     * Mybatis sqlSession
     */
    @Primary
    @Bean(name = "primarySqlSessionFactory")
    @ConditionalOnBean(name = {"primaryDataSource"})
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/business/*.xml"));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        bean.getObject().getConfiguration().setCallSettersOnNulls(true);
        return bean.getObject();
    }


    /**
     *  事务配置 声明式事务时 需要注明事务的bean 名称
     */
    @Primary
    @Bean(name = "transactionManager")
    @ConditionalOnBean(name = {"primaryDataSource"})
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
