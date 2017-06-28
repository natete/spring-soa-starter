package com.emergya.sss3E.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

/**
 * Class that provides the root application context.
 * 
 * @author iiglesias
 *
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource(value = { "classpath:app.properties" })
public class AppConfig {

}
