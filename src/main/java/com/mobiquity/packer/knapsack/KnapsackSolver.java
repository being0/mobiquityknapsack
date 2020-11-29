package com.mobiquity.packer.knapsack;

import com.mobiquity.packer.model.KnapsackProblem;
import com.mobiquity.packer.model.KnapsackSolution;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public interface KnapsackSolver {

    KnapsackSolution solve(KnapsackProblem knapsackProblem);
}
