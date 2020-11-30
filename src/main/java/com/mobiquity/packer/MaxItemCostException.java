package com.mobiquity.packer;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/30/2020
 */
public class MaxItemCostException extends ConstraintException {

    public MaxItemCostException(String message, Exception e) {
        super(message, e);
    }

    public MaxItemCostException(String message) {
        super(message);
    }
}
