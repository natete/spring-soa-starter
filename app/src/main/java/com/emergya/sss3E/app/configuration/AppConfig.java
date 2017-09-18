package com.emergya.sss3E.app.configuration;

import com.emergya.sss3E.persistence.configuration.JdbcConfig;
import com.emergya.sss3E.security.config.RedisConfig;
import com.emergya.sss3E.security.config.WebSecurityConfig;
import com.emergya.sss3E.swagger.configuration.SwaggerConfig;
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
@Import({ WebSecurityConfig.class, RedisConfig.class, JdbcConfig.class, SwaggerConfig.class })
public class AppConfig {

}
