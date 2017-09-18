package com.emergya.sss3E.persistence.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Values of the keys in the property resource jdbc.properties. Return the property value associated with the given key,
 * or null if the key cannot be resolved.
 *
 * @author iiglesias
 */
@Component
public class JdbcPropsValues {

    /*
     * NOTE: Sort the private variables in alphabetical order
     */

    @Value("${" + JdbcPropsKeys.HIBERNATE_DIALECT + "}")
    private String hibernateDialect;

    @Value("${" + JdbcPropsKeys.HIBERNATE_SHOW_SQL + "}")
    private String hibernateShowSql;

    @Value("${" + JdbcPropsKeys.JDBC_DRIVER_CLASS_NAME + "}")
    private String jdbcDriverClassName;

    @Value("${" + JdbcPropsKeys.JDBC_PASSWORD + "}")
    private String jdbcPassword;

    @Value("${" + JdbcPropsKeys.JDBC_URL + "}")
    private String jdbcUrl;

    @Value("${" + JdbcPropsKeys.JDBC_USERNAME + "}")
    private String jdbcUsername;

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public String getHibernateShowSql() {
        return hibernateShowSql;
    }

    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

}
