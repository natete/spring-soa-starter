package com.emergya.sss3E.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Manages Redis configuration.
 *
 * @author igonzalez
 * @since 04/07/17.
 */
@Configuration
@EnableRedisRepositories
@PropertySource(value = { "classpath:redis.properties" })
public class RedisConfig {

    @Value("${redis.hostname}")
    private String redisHostName;

    @Value("${redis.port}")
    private int redisPort;

    /**
     * Set up the Redis configuration.
     *
     * @return {@link RedisConnectionFactory} the configured Redis Connection Factory
     */
    @Bean
    RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHostName);
        factory.setPort(redisPort);
        return factory;
    }

    /**
     * Configure the Redis template.
     *
     * @param connectionFactory the {@link RedisConnectionFactory} to be used by the template.
     * @return {@link RedisTemplate} the template configuration.
     */
    @Bean
    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
