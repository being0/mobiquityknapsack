package com.mobiquity.packer;

import java.math.BigDecimal;

/**
 * Represents packer business constraints
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
class PackerConstraints {

    private final BigDecimal maxWeightConstraint;
    private final Integer maxNumberOfItemsConstraint;
    private final BigDecimal maxItemCostConstraint;
    private final BigDecimal maxItemWeightConstraint;

    private static final PackerConstraints defaults = new PackerConstraints(new BigDecimal("100"), 15,
            new BigDecimal("100"), new BigDecimal("100"));

    PackerConstraints(BigDecimal maxWeightConstraint, Integer maxNumberOfItemsConstraint,
                      BigDecimal maxItemCostConstraint, BigDecimal maxItemWeightConstraint) {
        this.maxWeightConstraint = maxWeightConstraint;
        this.maxNumberOfItemsConstraint = maxNumberOfItemsConstraint;
        this.maxItemCostConstraint = maxItemCostConstraint;
        this.maxItemWeightConstraint = maxItemWeightConstraint;
    }

    public static PackerConstraints getDefaults() {
        return defaults;
    }

    BigDecimal getMaxWeightConstraint() {
        return maxWeightConstraint;
    }

    Integer getMaxNumberOfItemsConstraint() {
        return maxNumberOfItemsConstraint;
    }

    BigDecimal getMaxItemCostConstraint() {
        return maxItemCostConstraint;
    }

    BigDecimal getMaxItemWeightConstraint() {
        return maxItemWeightConstraint;
    }
}
