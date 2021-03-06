package com.mobiquity.packer;

import com.mobiquity.packer.knapsack.KnapsackSolver;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.KnapsackProblem;
import com.mobiquity.packer.model.KnapsackSolution;
import com.mobiquity.packer.repository.KnapsackProblemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads problem from repository and then checks business constraint if constraint checks pass,
 * it solves the problems using KnapsackSolver.
 * <p>
 * The constraints that should be checked are:
 * 1. Max weight that a package can take
 * 2. Maximum number of items in the problem
 * 3. Max weight and cost of an item
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class DefaultPackerLogic implements PackerLogic {

    private final KnapsackProblemRepository knapsackProblemRepository;
    private final KnapsackSolver knapsackSolver;
    private final PackerConstraints packerConstraints;

    public DefaultPackerLogic(KnapsackProblemRepository knapsackProblemRepository, KnapsackSolver knapsackSolver,
                              PackerConstraints packerConstraints) {
        this.knapsackProblemRepository = knapsackProblemRepository;
        this.knapsackSolver = knapsackSolver;
        this.packerConstraints = packerConstraints;
    }

    @Override
    public List<KnapsackSolution> solveAll() {

        List<KnapsackSolution> solutions = new ArrayList<>();

        knapsackProblemRepository.executeOnAllEntries(problem -> {
            // Check business constrains here before solving the problem
            checkConstrains(problem);
            solutions.add(knapsackSolver.solve(problem));
        });

        return solutions;
    }

    private KnapsackProblem checkConstrains(KnapsackProblem problem) {
        // It is not obvious from the spec that what should happen in case of constrain fails, so here I raise exception

        if (packerConstraints.getMaxWeightConstraint().compareTo(problem.getMaxWeight()) < 0) {
            throw new MaxWeightException("Max weight can not be greater than "
                    + packerConstraints.getMaxWeightConstraint() + ", it is " + problem.getMaxWeight());
        }

        if (packerConstraints.getMaxNumberOfItemsConstraint().compareTo(problem.getItems().size()) < 0) {
            throw new MaxNumberOfItemsException("Max number of items can not be greater than "
                    + packerConstraints.getMaxNumberOfItemsConstraint() + ", it is " + problem.getItems().size());
        }

        for (Item item : problem.getItems()) {
            if (packerConstraints.getMaxItemCostConstraint().compareTo(item.getValue()) < 0) {
                throw new MaxItemCostException("Max item cost can not be greater than  "
                        + packerConstraints.getMaxItemCostConstraint() + ", it is " + item.getValue());
            }
            if (packerConstraints.getMaxItemWeightConstraint().compareTo(item.getWeight()) < 0) {
                throw new MaxItemWeightException("Max item weight can not be greater than  "
                        + packerConstraints.getMaxItemWeightConstraint() + ", it is " + item.getWeight());
            }
        }

        return problem;

    }

}
