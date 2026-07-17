package com.erp.application.exceptions;

/**
 * Thrown when a query (read operation) fails in a use case.
 * Extends RuntimeException because Spring handles exceptions at runtime;
 * it is meant to be caught globally by a @RestControllerAdvice.
 */
public class QueryException extends RuntimeException {

    public QueryException(String message) {
        super(message);
    }
}
