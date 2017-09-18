package com.emergya.sss3E.security.entrypoints;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manages not authenticated requests.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
@Component
class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Returns Unauthorized for non authenticated requests.
     *
     * @param httpServletRequest the received request.
     * @param httpServletResponse the response to be sent.
     * @param e the authentication exception thrown.
     * @throws IOException if there is an error writing the response.
     * @throws ServletException if there is an error handling the response in the servlet.
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
    }
}
