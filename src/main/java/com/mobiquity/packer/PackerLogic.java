package com.mobiquity.packer;

import com.mobiquity.packer.model.KnapsackSolution;

import java.util.List;

/**
 * This class is responsible to solve knapsack problems
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 12/02/2020
 */
public interface PackerLogic {

    /**
     * Solve all knapsack problems ans returns solutions respectively
     *
     * @return KnapsackSolution
     * @throws ConstraintException constraint exception is raise in case business constraints are violated
     */
    List<KnapsackSolution> solveAll() throws ConstraintException;
}
