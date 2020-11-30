package com.mobiquity.packer.repository;

import com.mobiquity.packer.model.KnapsackProblem;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public interface KnapsackProblemRepository {

    /**
     * Reads all knapsack problems from data repository.
     *
     * @return list of knapsack problems
     */
    List<KnapsackProblem> readAll();

    /**
     * Use this method for large repositories. This method should load one or small set of problems and then call the consumer
     *
     * @param consumer consumer that receives a KnapsackProblem
     */
    void executeOnAllEntries(Consumer<KnapsackProblem> consumer);
}
