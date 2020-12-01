package com.mobiquity.packer.repository;

import com.mobiquity.packer.model.KnapsackProblem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
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
 *
 * Caution: For large files use executeOnAllEntries method
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public class KnapsackProblemFileRepository implements KnapsackProblemRepository {

    private final String filePath;
    private final ProblemParser problemParser;

    public KnapsackProblemFileRepository(String filePath, ProblemParser problemParser) {
        if (filePath == null || filePath.isEmpty())
            throw new IllegalArgumentException("filePath can not be null or empty!");

        this.filePath = filePath;
        this.problemParser = problemParser;
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

            stream.forEach(l -> knapsackProblems.add(problemParser.parse(l)));

        } catch (IOException ioe) {
            throw new RepositoryException("Can't read from the file! Make sure file exists. File path is " + filePath, ioe);
        }

        return knapsackProblems;
    }

    /**
     * Executes a consumer on repository. Works for large files.
     *
     * @param consumer consumer that receives a KnapsackProblem
     */
    @Override
    public void executeOnAllEntries(Consumer<KnapsackProblem> consumer) {

        try (LineIterator it = FileUtils.lineIterator(Paths.get(filePath).toFile(), "UTF-8")) {

            while (it.hasNext()) {
                consumer.accept(problemParser.parse(it.nextLine()));
            }

        } catch (IOException ioe) {
            throw new RepositoryException("Can't read from the file! Make sure file exists. File path is " + filePath, ioe);
        }
    }
}
