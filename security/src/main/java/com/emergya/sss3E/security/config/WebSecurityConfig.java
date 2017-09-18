package com.emergya.sss3E.security.config;

import com.emergya.sss3E.security.filters.JwtTokenFilter;
import com.emergya.sss3E.security.providers.JwtLoginAuthenticationProvider;
import com.emergya.sss3E.security.providers.JwtTokenAuthenticationProvider;
import com.emergya.sss3E.security.utils.JwtFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.filter.CorsFilter;

/**
 * Manages the security configuration.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
@Configuration
@EnableWebSecurity
@PropertySource(value = { "classpath:security.propert"
        + "ies", "classpath:redis.properties" })
@ComponentScan(value = { "com.emergya.sss3E.security" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String API_ENDPOINTS = "/api/**";
    public static final String LOGIN_ENDPOINT = "/api/auth/login";
    public static final String REFRESH_TOKEN_ENDPOINT = "/api/auth/refresh";
    private static final String REVOKE_TOKEN_ENDPOINT = "/api/auth/logout";
    public static final String TOKEN_HEADER = "Authorization";

    private final CorsFilter corsFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtLoginAuthenticationProvider loginAuthenticationProvider;
    private final JwtTokenAuthenticationProvider tokenAuthenticationProvider;
    private final LogoutHandler logoutHandler;
    private final JwtFilterBuilder jwtFilterBuilder;

    @Autowired
    public WebSecurityConfig(CorsFilter corsFilter, AuthenticationEntryPoint authenticationEntryPoint,
            JwtLoginAuthenticationProvider loginAuthenticationProvider,
            JwtTokenAuthenticationProvider tokenAuthenticationProvider, LogoutHandler logoutHandler,
            @Lazy JwtFilterBuilder jwtFilterBuilder) {
        this.corsFilter = corsFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.logoutHandler = logoutHandler;
        this.jwtFilterBuilder = jwtFilterBuilder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

            .exceptionHandling()
            .authenticationEntryPoint(this.authenticationEntryPoint)

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, API_ENDPOINTS)
            .permitAll()

            .and()
            .authorizeRequests()
            .antMatchers(LOGIN_ENDPOINT, REFRESH_TOKEN_ENDPOINT)
            .permitAll()

            .and()
            .logout()
            .logoutUrl(REVOKE_TOKEN_ENDPOINT)
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))

            .and()
            .authorizeRequests()
            .antMatchers(API_ENDPOINTS)
            .permitAll()

            .and()
            .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
            // Filters to manage authentication
            .addFilterAfter(jwtFilterBuilder.buildJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtFilterBuilder.buildJwtLoginFilter(), JwtTokenFilter.class)
            .addFilterBefore(jwtFilterBuilder.buildJwtRefreshFilter(), JwtTokenFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Add here the authentication providers your application needs.
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
