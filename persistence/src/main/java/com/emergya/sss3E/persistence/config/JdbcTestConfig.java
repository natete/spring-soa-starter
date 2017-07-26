package com.emergya.sss3E.persistence.config;

import com.emergya.sss3E.persistence.commons.props.JdbcPropsValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Class that provides the JDBC application configuration.
 *
 * @author iiglesias
 */
@Profile("test")
@Configuration
@PropertySource(value = { "classpath:jdbc-test.properties" })
@ComponentScan(value = { "com.emergya.sss3E.persistence" })
@EnableJpaRepositories(basePackages = "com.emergya.sss3E.app.repository")
@EnableTransactionManagement
public class JdbcTestConfig extends JdbcConfig {

    @Autowired
    public JdbcTestConfig(JdbcPropsValues jdbcPropsValues) {
        super(jdbcPropsValues);
    }

    /**
     * Persistent set of properties for the JPA configuration EntityManagerFactory.
     *
     * @return
     */
    @Override
    protected Properties jpaProperties() {

        Properties props = super.jpaProperties();

        props.setProperty("hibernate.hbm2ddl.auto", jdbcPropsValues.getHibernateDdlAuto());

        return props;
    }
}
