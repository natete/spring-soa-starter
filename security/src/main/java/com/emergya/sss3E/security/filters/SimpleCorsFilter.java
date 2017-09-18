package com.emergya.sss3E.security.filters;

import com.emergya.sss3E.security.config.WebSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Manages Cors configuration.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class SimpleCorsFilter {

    /**
     * Bean that represents the Cors filter with the desired configuration.
     *
     * @return {@link CorsFilter} the Cors filter to be applied.
     */
    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "DELETE"));
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("content-type", "authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(WebSecurityConfig.API_ENDPOINTS, config);

        return new CorsFilter(source);
    }
}
