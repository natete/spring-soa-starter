package com.emergya.sss3E.security.handlers;

import com.emergya.sss3E.security.exceptions.JwtExpiredTokenException;
import com.emergya.sss3E.security.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles errors on authentication filters.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String BAD_CREDENTIALS_CODE = "13";
    private static final String EXPIRED_TOKEN_CODE = "14";
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Method to be called when the authentication fails.
     *
     * @param request
     *            the received request.
     * @param response
     *            the response to be sent.
     * @param e
     *            the authentication error registered.
     * @throws IOException
     *             if an error occurs writing the response.
     * @throws ServletException
     *             if an error occurs managing the Servlet.
     * @see AuthenticationFailureHandler#onAuthenticationFailure(HttpServletRequest,
     *      HttpServletResponse, AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {

        ErrorResponse errorResponse;

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // Manage different exception types such as token expired, bad credentials etc.
        if (e instanceof BadCredentialsException) {
            errorResponse = new ErrorResponse(BAD_CREDENTIALS_CODE, "Bad Credentials");
        } else if (e instanceof JwtExpiredTokenException) {
            errorResponse = new ErrorResponse(EXPIRED_TOKEN_CODE, "Expired token");
        } else {
            errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), "Unauthorized");
        }

        mapper.writeValue(response.getWriter(), errorResponse);
    }
}
