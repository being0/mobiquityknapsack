package com.mobiquity.packer.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents knapsack problem model
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class KnapsackProblem {

    /**
     * Maximum weight that Knapsack can carry
     */
    private final BigDecimal maxWeight;

    /**
     * Possible items that can be used to solve the problem
     */
    private final List<Item> items;

    public KnapsackProblem(BigDecimal maxWeight, List<Item> items) {
        this.maxWeight = maxWeight;
        this.items = items;
    }

    public BigDecimal getMaxWeight() {
        return maxWeight;
    }

    public List<Item> getItems() {
        return items;
    }
}
