package com.emergya.sss3e.configuration;

import com.emergya.sss3e.security.config.RedisConfig;
import com.emergya.sss3e.security.config.WebSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
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
@PropertySource(value = { "classpath:app.properties" })
@ComponentScan(value = { "com.emergya.sss3e" })
@Import({ WebSecurityConfig.class, RedisConfig.class })
public class AppConfig {

}
