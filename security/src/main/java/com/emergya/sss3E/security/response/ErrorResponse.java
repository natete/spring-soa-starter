package com.emergya.sss3E.security.response;

import com.emergya.sss3E.security.handlers.JwtAuthenticationFailureHandler;

import java.io.Serializable;

/**
 * Response object for the rest service when an error has occurred. Normally, this response is used in the handler
 * {@link JwtAuthenticationFailureHandler}.
 *
 * @author iiglesias
 */
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 4284141687310242358L;

    /**
     * Code of the error
     */
    private String code;

    /**
     * Message of the error
     */
    private String message;

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse [code=" + code + ", message=" + message + "]";
    }

}
