package com.mobiquity.packer.repository;

import com.mobiquity.packer.model.KnapsackProblem;

import java.util.List;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public interface KnapsackProblemRepository {

    /**
     * Reads all knapsack problems from data repository.
     * @return list of knapsack problems
     */
    List<KnapsackProblem> readAll() throws ParseException;
}
