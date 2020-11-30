package com.mobiquity.packer.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class KnapsackSolution {

    private final List<Item> items;
    private final BigDecimal weight;
    private final BigDecimal value;

    public KnapsackSolution(List<Item> items, BigDecimal weight, BigDecimal value) {
        this.items = items;
        this.weight = weight;
        this.value = value;
    }

    public List<Item> getItems() {
        return items;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getValue() {
        return value;
    }

}
