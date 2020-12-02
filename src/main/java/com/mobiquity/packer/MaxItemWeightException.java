package com.mobiquity.packer;

/**
 * Raise in case item weight constraint is violated
 *i
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/30/2020
 */
public class MaxItemWeightException extends ConstraintException {

    public MaxItemWeightException(String message, Exception e) {
        super(message, e);
    }

    public MaxItemWeightException(String message) {
        super(message);
    }
}
