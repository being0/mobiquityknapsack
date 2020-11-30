package com.mobiquity.packer.repository;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.KnapsackProblem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This implementation provides access to the file repository.
 * File repository should be in the following format:
 * Each line contains the weight that the package can take (before the colon) and the list of items you need
 * to choose. Each item is enclosed in parentheses where the 1 st number is a item’s index number, the 2 nd
 * is its weight and the 3 rd is its cost. E.g.
 * 81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9)
 * (6,46.34,€48)
 * 8 : (1,15.3,€34)
 * 75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52)
 * (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)
 * 56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36)
 * (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)
 * <p>
 * Caution: This solution should not be used for very large files.
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class KnapsackProblemFileRepository implements KnapsackProblemRepository {

    private final String filePath;
    private final LineParser lineParser;

    public KnapsackProblemFileRepository(String filePath, LineParser lineParser) {
        if (filePath == null || filePath.isEmpty())
            throw new IllegalArgumentException("filePath can not be null or empty!");

        this.filePath = filePath;
        this.lineParser = lineParser;
    }

    /**
     * Reads all knapsack problems from file.
     *
     * @return list of knapsack problems
     */
    @Override
    public List<KnapsackProblem> readAll() {

        ArrayList<KnapsackProblem> knapsackProblems = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {

            stream.forEach(l -> knapsackProblems.add(lineParser.parse(l)));

        } catch (IOException ioe) {
            throw new RepositoryException("Can't read from the file! Make sure file exists. File path is " + filePath, ioe);
        }

        return knapsackProblems;
    }


}
