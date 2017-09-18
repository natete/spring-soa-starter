package com.emergya.sss3E.security.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Class that holds the bean of the password encoder used in the application.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Bean of the password encoder used in the application.
     *
     * @return the password encoder.
     */
    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
