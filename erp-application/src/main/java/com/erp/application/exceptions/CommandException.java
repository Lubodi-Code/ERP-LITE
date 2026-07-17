package com.erp.application.exceptions;

/**
 * Thrown when a command (write operation) fails in a use case.
 * Extends RuntimeException because Spring handles exceptions at runtime;
 * it is meant to be caught globally by a @RestControllerAdvice.
 */
public class CommandException extends RuntimeException {

    public CommandException(String message) {
        super(message);
    }
}
