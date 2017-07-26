package com.emergya.sss3E.persistence.config;

import com.emergya.sss3E.persistence.commons.props.JdbcPropsValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Class that provides the JDBC application configuration.
 *
 * @author iiglesias
 */
@Configuration
@PropertySource(value = { "classpath:jdbc.properties" })
@ComponentScan(value = { "com.emergya.sss3E.persistence" })
@EnableJpaRepositories(basePackages = "com.emergya.sss3E.app.repository")
@EnableTransactionManagement
public class JdbcConfig {

    protected final JdbcPropsValues jdbcPropsValues;

    @Autowired
    public JdbcConfig(JdbcPropsValues jdbcPropsValues) {
        this.jdbcPropsValues = jdbcPropsValues;
    }

    /**
     * Simple implementation of the standard JDBC {@link DataSource} interface,
     * configuring the plain old JDBC {@link DriverManager} via bean properties, and
     * returning a new java.sql.Connection from every getConnection call.
     *
     * @return
     */
    @Bean
    public DriverManagerDataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(jdbcPropsValues.getJdbcDriverClassName());
        dataSource.setUrl(jdbcPropsValues.getJdbcUrl());
        dataSource.setUsername(jdbcPropsValues.getJdbcUsername());
        dataSource.setPassword(jdbcPropsValues.getJdbcPassword());

        return dataSource;
    }

    /**
     * Creates a JPA javax.persistence.EntityManagerFactory according to JPA's
     * standard container bootstrap contract.
     *
     * @param datasource
     * @return
     */
    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource datasource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("com.emergya.sss3E.app.model");

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setGenerateDdl(true);

        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);

        entityManagerFactory.setJpaProperties(jpaProperties());

        return entityManagerFactory;
    }

    /**
     * Implementation for a single JPA {@link EntityManagerFactory}
     *
     * @param entityManagerFactory
     * @return
     */
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    /**
     * Persistent set of properties for the JPA configuration EntityManagerFactory.
     *
     * @return
     */
    protected Properties jpaProperties() {

        Properties props = new Properties();

        props.setProperty("hibernate.dialect", jdbcPropsValues.getHibernateDialect());
        props.setProperty("hibernate.show_sql", jdbcPropsValues.getHibernateShowSql());

        return props;
    }
}
