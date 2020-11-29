package com.mobiquity.packer.repository;

import com.mobiquity.packer.model.KnapsackProblem;

/**
 * @author <a href="mailto:raliakbari@gmail.com">Reza Aliakbari</a>
 * @version 1, 11/29/2020
 */
public interface LineParser {

    KnapsackProblem parse(String line) throws ParseException;
}
