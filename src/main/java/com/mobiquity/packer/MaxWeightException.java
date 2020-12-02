package com.mobiquity.packer;

/**
 * Raise if maximum weight constraint in problem is violated
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/30/2020
 */
public class MaxWeightException extends ConstraintException {

    public MaxWeightException(String message, Exception e) {
        super(message, e);
    }

    public MaxWeightException(String message) {
        super(message);
    }
}
