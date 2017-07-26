package com.emergya.sss3E.app.configuration;

import com.emergya.sss3E.persistence.config.JdbcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Class that provides the root application context.
 *
 * @author iiglesias
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource(value = { "classpath:swagger.properties" })
//@ComponentScan(value = { "com.emergya.sss3E.app" })
@Import({ JdbcConfig.class })
public class AppConfig {

}
