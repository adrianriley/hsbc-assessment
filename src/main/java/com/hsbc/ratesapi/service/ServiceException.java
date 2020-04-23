package com.hsbc.ratesapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wrapper for exceptions thrown in service classes
 */
public class ServiceException extends RuntimeException {
    /**
     * Constructor
     * @param message descriptive message which cane be dsplayed to a user
     * @param cause underlying cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
