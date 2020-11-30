package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.knapsack.LcBranchAndBoundKnapsackSolver;
import com.mobiquity.packer.model.KnapsackSolution;
import com.mobiquity.packer.repository.KnapsackProblemFileRepository;
import com.mobiquity.packer.repository.KnapsackProblemRepository;
import com.mobiquity.packer.repository.LineParser;
import com.mobiquity.packer.repository.RegexLineParser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {

        // Here For simplicity I do the role of an IOC framework(like Spring) and construct and inject dependencies
        // Config repository
        LineParser parser = new RegexLineParser();
        KnapsackProblemRepository repository = new KnapsackProblemFileRepository(filePath, parser);
        // Config packer logic
        PackerLogic packerLogic = new DefaultPackerLogic(repository, new LcBranchAndBoundKnapsackSolver(), PackerConstraints.getDefaults());

        // Run packer logic to solve all problems
        List<KnapsackSolution> solutions = packerLogic.solveAll();

        return convertSolutionToString(solutions);
    }

    private static String convertSolutionToString(List<KnapsackSolution> solutions) {

        StringBuilder strBuilder = new StringBuilder();
        for (KnapsackSolution solution : solutions) {
            strBuilder.append(solution.getItems().size() == 0 ? "-" :
                    solution.getItems().stream().map(i -> String.valueOf(i.getIndex()))
                            .collect(Collectors.joining(",")));
            strBuilder.append("\n");
        }

        return strBuilder.toString();
    }
}
