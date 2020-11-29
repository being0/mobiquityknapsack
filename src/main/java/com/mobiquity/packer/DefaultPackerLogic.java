package com.mobiquity.packer;

import com.mobiquity.packer.knapsack.KnapsackSolver;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.model.KnapsackProblem;
import com.mobiquity.packer.model.KnapsackSolution;
import com.mobiquity.packer.repository.KnapsackProblemRepository;

import java.util.ArrayList;
import java.util.List;

/**
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

        List<KnapsackProblem> problems = knapsackProblemRepository.readAll();
        List<KnapsackSolution> solutions = new ArrayList<>();

        for (KnapsackProblem problem : problems) {
            // Check business constrains here before solving the problem
            checkConstrains(problem);
            solutions.add(knapsackSolver.solve(problem));
        }

        return solutions;
    }

    private void checkConstrains(KnapsackProblem problem) {
        // It is not obvious from the spec that what should happen in case of constrain fails, so here I raise exception

        if (packerConstraints.getMaxWeightConstraint().compareTo(problem.getMaxWeight()) < 0) {
            throw new ConstraintException("Max weight can not be greater than "
                    + packerConstraints.getMaxWeightConstraint() + ", it is " + problem.getMaxWeight());
        }

        if (packerConstraints.getMaxNumberOfItemsConstraint().compareTo(problem.getItems().size()) < 0) {
            throw new ConstraintException("Max number of items can not be greater than "
                    + packerConstraints.getMaxNumberOfItemsConstraint() + ", it is " + problem.getItems().size());
        }

        for (Item item : problem.getItems()) {
            if (packerConstraints.getMaxItemCostConstraint().compareTo(item.getCost()) < 0) {
                throw new ConstraintException("Max item cost can not be greater than  "
                        + packerConstraints.getMaxItemCostConstraint() + ", it is " + item.getCost());
            }
            if (packerConstraints.getMaxItemWeightConstraint().compareTo(item.getWeight()) < 0) {
                throw new ConstraintException("Max item weight can not be greater than  "
                        + packerConstraints.getMaxItemWeightConstraint() + ", it is " + item.getWeight());
            }
        }


    }

}
