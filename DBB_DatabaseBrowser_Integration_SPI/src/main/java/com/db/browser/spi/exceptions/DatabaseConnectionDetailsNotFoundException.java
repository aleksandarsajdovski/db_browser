package com.db.browser.spi.exceptions;

/**
 * Not Found common exception.
 */
public class DatabaseConnectionDetailsNotFoundException extends RuntimeException {

    /**
     * Constructor with only exception message.
     *
     * @param message the exception message.
     */
    public DatabaseConnectionDetailsNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with exception message and cause.
     *
     * @param message the exception message.
     * @param cause the cause.
     */
    public DatabaseConnectionDetailsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}