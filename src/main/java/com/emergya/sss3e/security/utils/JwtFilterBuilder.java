package com.emergya.sss3e.security.utils;

import com.emergya.sss3e.security.config.WebSecurityConfig;
import com.emergya.sss3e.security.filters.JwtLoginFilter;
import com.emergya.sss3e.security.filters.JwtRefreshFilter;
import com.emergya.sss3e.security.filters.JwtTokenFilter;
import com.emergya.sss3e.security.jwt.extractor.TokenExtractor;
import com.emergya.sss3e.security.matchers.SkipPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Creates the filters to be used in the application.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
@Component
public class JwtFilterBuilder {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    private final AuthenticationManager authenticationManager;

    /**
     * Default constructor
     *
     * @param successHandler an instance of the class to be used to handle the success on the authentication process.
     * @param failureHandler an instance of the class to be used to handle the failure on the authentication process.
     * @param tokenExtractor an instance of the class to be used to extract the token from the header.
     * @param authenticationManager the authentication manager to be used by the filters.
     */
    @Autowired
    public JwtFilterBuilder(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler,
            TokenExtractor tokenExtractor, AuthenticationManager authenticationManager) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Builds a filter to be used by the requests that are issued with a JWT token as a authenticator.
     *
     * @return the filter.
     */
    public JwtTokenFilter buildJwtTokenFilter() {

        List<String> pathsToSkip = Arrays
                .asList(WebSecurityConfig.LOGIN_ENDPOINT, WebSecurityConfig.REFRESH_TOKEN_ENDPOINT);

        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, WebSecurityConfig.API_ENDPOINTS);

        JwtTokenFilter filter = new JwtTokenFilter(failureHandler, tokenExtractor, matcher);

        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    /**
     * Builds a filter to be used by the login requests.
     *
     * @return the filter.
     */
    public JwtLoginFilter buildJwtLoginFilter() {
        JwtLoginFilter filter = new JwtLoginFilter(WebSecurityConfig.LOGIN_ENDPOINT, successHandler, failureHandler);

        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    /**
     * Builds a filter to be used by the refresh token requests.
     *
     * @return the filter.
     */
    public JwtRefreshFilter buildJwtRefreshFilter() {
        JwtRefreshFilter filter = new JwtRefreshFilter(WebSecurityConfig.REFRESH_TOKEN_ENDPOINT, successHandler, failureHandler);

        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }
}
