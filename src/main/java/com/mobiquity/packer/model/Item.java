package com.mobiquity.packer.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class Item {

    /**
     * Index stating from 1
     */
    private final Integer index;
    /**
     * Weight of item
     */
    private final BigDecimal weight;
    /**
     * Value of item
     */
    private final BigDecimal value;
    /**
     * Ration of value/weight is used for solving problem
     */
    private final BigDecimal ratio;

    public Item(Integer index, BigDecimal weight, BigDecimal value) {
        this.index = index;
        this.weight = weight;
        this.value = value;
        this.ratio = value.divide(weight, 10, RoundingMode.HALF_UP);
    }

    public Integer getIndex() {
        return index;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

}