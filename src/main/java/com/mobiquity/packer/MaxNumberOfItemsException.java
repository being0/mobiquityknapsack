package com.mobiquity.packer;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/30/2020
 */
public class MaxNumberOfItemsException extends ConstraintException {

    public MaxNumberOfItemsException(String message, Exception e) {
        super(message, e);
    }

    public MaxNumberOfItemsException(String message) {
        super(message);
    }
}
