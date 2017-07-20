package com.emergya.sss3E.app.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * Class that provides the root application context.
 *
 * @author iiglesias
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource(value = { "classpath:swagger.properties" })
@ComponentScan(value = { "com.emergya.sss3E.app" })
public class AppConfig {

}
