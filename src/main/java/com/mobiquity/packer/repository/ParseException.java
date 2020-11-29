package com.mobiquity.packer.repository;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class ParseException extends RepositoryException {

    public ParseException(String message, Exception e) {
        super(message, e);
    }

    public ParseException(String message) {
        super(message);
    }
}
