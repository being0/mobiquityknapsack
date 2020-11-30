package com.mobiquity.packer.knapsack

import com.mobiquity.packer.model.Item
import com.mobiquity.packer.model.KnapsackProblem
import com.mobiquity.packer.model.KnapsackSolution
import spock.lang.Specification

class LcBAndBKnapsackSolverSpec extends Specification {

    LcBAndBKnapsackSolver solver = new LcBAndBKnapsackSolver()

    def 'When there are same value solution pick less heavy'() {
        given:
        List<Item> items = [new Item(1, 90.72 as BigDecimal, 13 as BigDecimal),
                            new Item(2, 33.80 as BigDecimal, 40 as BigDecimal),
                            new Item(3, 43.15 as BigDecimal, 10 as BigDecimal),
                            new Item(4, 37.97 as BigDecimal, 16 as BigDecimal),
                            new Item(5, 46.81 as BigDecimal, 36 as BigDecimal),
                            new Item(6, 48.77 as BigDecimal, 79 as BigDecimal),
                            new Item(7, 81.80 as BigDecimal, 45 as BigDecimal),
                            new Item(8, 19.36 as BigDecimal, 79 as BigDecimal),
                            new Item(9, 6.76 as BigDecimal, 64 as BigDecimal)]
        KnapsackProblem problem = new KnapsackProblem(56 as BigDecimal, items)
        when:
        KnapsackSolution solution = solver.solve(problem)
        then:
        solution.weight == 26.12
        solution.value == 143
        solution.items.size() == 2
        solution.items[0].index == 8 // Two last items are selected
        solution.items[1].index == 9
    }

    def 'Boundary null: no solution'() {
        when:
        KnapsackSolution solution = solver.solve(null)
        then:
        solution.items.isEmpty()
        solution.value == 0
        solution.weight == 0
    }

    def 'Boundary empty items: no solution'() {
        when:
        KnapsackSolution solution = solver.solve(new KnapsackProblem(10 as BigDecimal, Collections.emptyList()))
        then:
        solution.items.isEmpty()
        solution.value == 0
        solution.weight == 0
    }

    def 'Boundary no solution with items'() {
        given:
        List<Item> items = [new Item(1, 90.72 as BigDecimal, 13 as BigDecimal),
                            new Item(2, 30.76 as BigDecimal, 64 as BigDecimal)]
        KnapsackProblem problem = new KnapsackProblem(27.9 as BigDecimal, items)
        when:
        KnapsackSolution solution = solver.solve(problem)
        then:
        solution.items.isEmpty()
        solution.value == 0
        solution.weight == 0
    }

    def 'Boundary minus weight: no result'() {
        given:
        List<Item> items = [new Item(1, 90.72 as BigDecimal, 13 as BigDecimal),
                            new Item(2, 30.76 as BigDecimal, 64 as BigDecimal)]
        KnapsackProblem problem = new KnapsackProblem(-27.9 as BigDecimal, items)
        when:
        KnapsackSolution solution = solver.solve(problem)
        then:
        solution.items.isEmpty()
        solution.value == 0
        solution.weight == 0
    }

    def 'Boundary 0 weight: no result'() {
        given:
        List<Item> items = [new Item(1, 90.72 as BigDecimal, 13 as BigDecimal),
                            new Item(2, 30.76 as BigDecimal, 64 as BigDecimal)]
        KnapsackProblem problem = new KnapsackProblem(0 as BigDecimal, items)
        when:
        KnapsackSolution solution = solver.solve(problem)
        then:
        solution.items.isEmpty()
        solution.value == 0
        solution.weight == 0
    }
}
