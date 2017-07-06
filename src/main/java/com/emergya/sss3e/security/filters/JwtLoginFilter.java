package com.emergya.sss3e.security.filters;

import com.emergya.sss3e.security.model.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * The filter to be applied on login requests.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final ObjectMapper mapper;

    /**
     * Default constructor.
     *
     * @param url the url to be filtered.
     * @param successHandler the method to be called on authentication success.
     * @param failureHandler the method to be called on authentication error.
     */
    public JwtLoginFilter(String url, AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler) {

        super(new AntPathRequestMatcher(url));
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.mapper = new ObjectMapper();
    }

    /**
     * Processes the received request to extract the login information.
     *
     * @param request the received request.
     * @param response the response to be sent.
     * @return {@link Authentication} the authentication result.
     * @throws AuthenticationException if the authentication parameters are invalid.
     * @throws IOException if an error occurs writing the response.
     * @throws ServletException if an error occurs managing the Servlet.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        LoginRequest user = this.mapper.readValue(request.getInputStream(), LoginRequest.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    /**
     * Method to be called when the authentication process succeeds.
     *
     * @param request the request received.
     * @param response the response to be sent.
     * @param chain the filter chain to be applied.
     * @param authResult the result of the authentication process to allow the handler extract the authenticated user.
     * @throws IOException if an error occurs writing the response.
     * @throws ServletException if an error occurs managing the Servlet.
     * @see AbstractAuthenticationProcessingFilter#successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);

        chain.doFilter(request, response);
    }

    /**
     * Method to be called whe the authentication process fails.
     *
     * @param request the request received.
     * @param response the response to be sent.
     * @param failed the authentication error that produced the failure.
     * @throws IOException if an error occurs writing the response.
     * @throws ServletException if an error occurs managing the Servlet.
     * @see AbstractAuthenticationProcessingFilter#unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
