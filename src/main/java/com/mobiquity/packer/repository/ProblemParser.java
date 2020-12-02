package com.mobiquity.packer.repository;

import com.mobiquity.packer.model.KnapsackProblem;

/**
 * Parse a string into a KnapsackProblem
 *
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public interface ProblemParser {

    /**
     * Parse a string into a KnapsackProblem
     *
     * @param line a string that should be parsed
     * @return Knapsack problem
     * @throws ParseException raise ParseException if the string can not be parsed
     */
    KnapsackProblem parse(String line) throws ParseException;
}
