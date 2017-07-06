package com.emergya.sss3e.security.filters;

import com.emergya.sss3e.security.config.WebSecurityConfig;
import com.emergya.sss3e.security.jwt.extractor.TokenExtractor;
import com.emergya.sss3e.security.model.JwtAuthenticationToken;
import com.emergya.sss3e.security.model.JwtRawAccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The filter to be applied on secured requests.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;

    /**
     * Default constructor.
     *
     * @param failureHandler the method to be called on authentication error.
     * @param tokenExtractor an instance of the {@link TokenExtractor} to extract the token from the header.
     * @param requestMatcher a matcher to indicate the requests to be filtered using this filter.
     */
    public JwtTokenFilter(AuthenticationFailureHandler failureHandler, TokenExtractor tokenExtractor,
            RequestMatcher requestMatcher) {
        super(requestMatcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    /**
     * Processes the received request to extract the token information.
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

        String tokenPayload = request.getHeader(WebSecurityConfig.TOKEN_HEADER);

        JwtRawAccessToken rawAccessToken = new JwtRawAccessToken(tokenExtractor.extract(tokenPayload));

        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(rawAccessToken));
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

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
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

        SecurityContextHolder.clearContext();
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
