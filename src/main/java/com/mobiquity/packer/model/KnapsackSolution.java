package com.mobiquity.packer.model;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class KnapsackSolution {

    private final Set<Item> items;
    private final BigDecimal capacity;
    private final BigDecimal price;

    public KnapsackSolution(Set<Item> items, BigDecimal capacity, BigDecimal price) {
        this.items = items;
        this.capacity = capacity;
        this.price = price;
    }

    public Set<Item> getItems() {
        return items;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
