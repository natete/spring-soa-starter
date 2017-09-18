package com.emergya.sss3E.security.filters;

import com.emergya.sss3E.security.model.JwtAuthenticationToken;
import com.emergya.sss3E.security.model.JwtRawAccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The filter to be applied on refresh token requests.
 *
 * @author igonzalez
 * @since 04/07/17.
 */
public class JwtRefreshFilter extends JwtLoginFilter {

    private final ObjectMapper mapper;

    /**
     * Default constructor.
     *
     * @param url the url to be filtered.
     * @param successHandler the method to be called on authentication success.
     * @param failureHandler the method to be called on authentication error.
     */
    public JwtRefreshFilter(String url, AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler) {
        super(url, successHandler, failureHandler);
        this.mapper = new ObjectMapper();
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
        String refreshToken = this.mapper.readValue(request.getInputStream(), String.class);

        JwtRawAccessToken rawAccessToken = new JwtRawAccessToken(refreshToken);

        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(rawAccessToken));
    }
}
