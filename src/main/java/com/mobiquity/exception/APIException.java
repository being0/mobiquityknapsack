package com.mobiquity.exception;

/**
 * Raise API exception in case an exception in this API occurs.
 * Extend this class to have specific exceptions for different purposes.
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 12/02/2020
 */
public class APIException extends RuntimeException {

    public APIException(String message, Exception e) {
        super(message, e);
    }

    public APIException(String message) {
        super(message);
    }
}
