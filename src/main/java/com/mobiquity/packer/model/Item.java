package com.mobiquity.packer.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class Item {

    private final Integer index;
    private final BigDecimal weight;
    private final BigDecimal value;
    private final BigDecimal valuePerWeight;

    public Item(Integer index, BigDecimal weight, BigDecimal value) {
        this.index = index;
        this.weight = weight;
        this.value = value;
        this.valuePerWeight = value.divide(weight, 10, RoundingMode.HALF_UP);
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

    public BigDecimal getValuePerWeight() {
        return valuePerWeight;
    }
}