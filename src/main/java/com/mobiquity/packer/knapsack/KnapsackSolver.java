package com.mobiquity.packer.knapsack;

import com.mobiquity.packer.model.KnapsackProblem;
import com.mobiquity.packer.model.KnapsackSolution;

/**
 * Knapsack algorithm implements this interface. It receives a knapsack problem and solve it and then returns the solution.
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public interface KnapsackSolver {

    /**
     * Solves knapsack problem
     *
     * @param knapsackProblem knapsack problem
     * @return knapsack solution
     */
    KnapsackSolution solve(KnapsackProblem knapsackProblem);
}
