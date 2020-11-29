package com.mobiquity.packer;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class PackerConstraints {

    private final BigDecimal maxWeightConstraint;
    private final Integer maxNumberOfItemsConstraint;
    private final BigDecimal maxItemCostConstraint;
    private final BigDecimal maxItemWeightConstraint;

    public PackerConstraints(BigDecimal maxWeightConstraint, Integer maxNumberOfItemsConstraint,
                             BigDecimal maxItemCostConstraint, BigDecimal maxItemWeightConstraint) {
        this.maxWeightConstraint = maxWeightConstraint;
        this.maxNumberOfItemsConstraint = maxNumberOfItemsConstraint;
        this.maxItemCostConstraint = maxItemCostConstraint;
        this.maxItemWeightConstraint = maxItemWeightConstraint;
    }

    public BigDecimal getMaxWeightConstraint() {
        return maxWeightConstraint;
    }

    public Integer getMaxNumberOfItemsConstraint() {
        return maxNumberOfItemsConstraint;
    }

    public BigDecimal getMaxItemCostConstraint() {
        return maxItemCostConstraint;
    }

    public BigDecimal getMaxItemWeightConstraint() {
        return maxItemWeightConstraint;
    }
}
