package com.emergya.sss3e.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Class that provides the root application context.
 * 
 * @author iiglesias
 *
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(value = { "com.emergya" })
@PropertySource(value = { "classpath:app.properties" })
@Import({ JdbcConfig.class })
public class AppConfig {

}
