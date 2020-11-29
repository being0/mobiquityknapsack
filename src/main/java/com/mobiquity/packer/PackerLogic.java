package com.mobiquity.packer;

import com.mobiquity.packer.model.KnapsackSolution;

import java.util.List;

public interface PackerLogic {

    List<KnapsackSolution> solveAll() throws ConstraintException;
}
