package com.mobiquity.packer;

import com.mobiquity.exception.APIException;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class ConstraintException extends APIException {

    public ConstraintException(String message, Exception e) {
        super(message, e);
    }

    public ConstraintException(String message) {
        super(message);
    }
}
