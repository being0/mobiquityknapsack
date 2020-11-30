package com.mobiquity.packer.model;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class KnapsackSolution {

    private final Set<Item> items;
    private final BigDecimal weight;
    private final BigDecimal value;

    public KnapsackSolution(Set<Item> items, BigDecimal weight, BigDecimal value) {
        this.items = items;
        this.weight = weight;
        this.value = value;
    }

    public Set<Item> getItems() {
        return items;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getValue() {
        return value;
    }
}
