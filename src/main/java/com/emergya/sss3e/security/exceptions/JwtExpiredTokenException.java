package com.emergya.sss3e.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception thrown when an expired token has been used.
 */
public class JwtExpiredTokenException extends AuthenticationException {

    /**
     * Default constructor.
     *
     * @param msg the message of the exception.
     * @param t the parent exception thrown.
     */
    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
