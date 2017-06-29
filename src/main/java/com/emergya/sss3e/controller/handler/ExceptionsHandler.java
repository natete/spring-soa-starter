package com.emergya.sss3e.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.emergya.sss3e.controller.response.ErrorResponse;

/**
 * Exception handler raised from the controllers. Controllers that want to control exceptions through this handler
 * should extend to this class.
 * 
 * @author iiglesias
 *
 */
public class ExceptionsHandler {

    /**
     * Handler for {@link Exception} class with Internal Server Error code
     * 
     * @param e Exception
     * 
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getLocalizedMessage()));
    }

}
