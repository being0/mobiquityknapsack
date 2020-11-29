package com.mobiquity.packer.model;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class Item {

    private final Integer index;
    private final BigDecimal weight;
    private final BigDecimal cost;

    public Item(Integer index, BigDecimal weight, BigDecimal cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public Integer getIndex() {
        return index;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
