package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.knapsack.LcBAndBKnapsackSolver;
import com.mobiquity.packer.model.KnapsackSolution;
import com.mobiquity.packer.repository.KnapsackProblemFileRepository;
import com.mobiquity.packer.repository.KnapsackProblemRepository;
import com.mobiquity.packer.repository.ProblemParser;
import com.mobiquity.packer.repository.RegexProblemParser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class Packer {

    private Packer() {
    }

    /**
     * Solves problem from file and generate output(Refer to the specification for the formats)
     * Do not use this class for API use, instead config and use PackerLogic.
     *
     * @param filePath path of file
     * @return output
     * @throws APIException ApiException in case known business exception happens
     */
    public static String pack(String filePath) throws APIException {

        // Here For simplicity I do the role of an IOC framework(like Spring) and construct and inject dependencies
        PackerLogic packerLogic = createPackerLogic(filePath);

        // Run packer logic to solve all problems
        List<KnapsackSolution> solutions = packerLogic.solveAll();

        return convertSolutionToString(solutions);
    }

    private static PackerLogic createPackerLogic(String filePath) {
        // Config repository
        ProblemParser parser = new RegexProblemParser();
        KnapsackProblemRepository repository = new KnapsackProblemFileRepository(filePath, parser);
        // Config packer logic
        return new DefaultPackerLogic(repository, new LcBAndBKnapsackSolver(), PackerConstraints.getDefaults());
    }

    private static String convertSolutionToString(List<KnapsackSolution> solutions) {

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < solutions.size(); i++) {
            KnapsackSolution solution = solutions.get(i);

            strBuilder.append(solution.getItems().size() == 0 ? "-" :
                    solution.getItems().stream().map(it -> String.valueOf(it.getIndex()))
                            .collect(Collectors.joining(",")));

            if (i != solutions.size() - 1)
                strBuilder.append("\n");
        }

        return strBuilder.toString();
    }
}
