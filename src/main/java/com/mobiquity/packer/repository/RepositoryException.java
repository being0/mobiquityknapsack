package com.mobiquity.packer.repository;

import com.mobiquity.exception.APIException;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class RepositoryException extends APIException {

    public RepositoryException(String message, Exception e) {
        super(message, e);
    }

    public RepositoryException(String message) {
        super(message);
    }
}
