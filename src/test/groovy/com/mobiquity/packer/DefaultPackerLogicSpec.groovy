package com.mobiquity.packer

import com.mobiquity.packer.knapsack.KnapsackSolver
import com.mobiquity.packer.model.Item
import com.mobiquity.packer.model.KnapsackProblem
import com.mobiquity.packer.repository.KnapsackProblemRepository
import spock.lang.Specification

import java.util.function.Consumer

class DefaultPackerLogicSpec extends Specification {

    DefaultPackerLogic packerLogic
    KnapsackProblemRepository repository
    KnapsackSolver knapsackSolver
    PackerConstraints defaultConstraint


    def setup() {
        repository = Stub(KnapsackProblemRepository)
        knapsackSolver = Stub(KnapsackSolver)
        defaultConstraint = new PackerConstraints(new BigDecimal("100"), 5,
                new BigDecimal("100"), new BigDecimal("100"))
    }

    def 'ConstraintException when max weight constraint is violated'() {
        given:
        packerLogic = new DefaultPackerLogic(repository, knapsackSolver, defaultConstraint)
        repository.executeOnAllEntries(_ as Consumer<KnapsackProblem>) >> { Consumer consumer ->
            consumer.accept(new KnapsackProblem(new BigDecimal("100.0001"), Collections.emptyList()))
        }
        when:
        packerLogic.solveAll()
        then:
        thrown(MaxWeightException)
    }

    def 'ConstraintException when max number of items constraint is violated'() {
        given:
        packerLogic = new DefaultPackerLogic(repository, knapsackSolver, defaultConstraint)
        KnapsackProblem problem = new KnapsackProblem(new BigDecimal("80"),
                [new Item(1, 2 as BigDecimal, 8 as BigDecimal), new Item(2, 5 as BigDecimal, 18 as BigDecimal),
                 new Item(2, 3 as BigDecimal, 9 as BigDecimal), new Item(2, 5 as BigDecimal, 8 as BigDecimal),
                 new Item(2, 4 as BigDecimal, 10 as BigDecimal), new Item(2, 5 as BigDecimal, 8 as BigDecimal)])

        repository.executeOnAllEntries(_ as Consumer<KnapsackProblem>) >> { Consumer consumer ->
            consumer.accept(problem)
        }
        when:
        packerLogic.solveAll()
        then:
        thrown(MaxNumberOfItemsException)
    }

    def 'ConstraintException when an item max cost constraint is violated'() {
        given:
        packerLogic = new DefaultPackerLogic(repository, knapsackSolver, defaultConstraint)
        KnapsackProblem problem = new KnapsackProblem(new BigDecimal("80"),
                [new Item(1, 2 as BigDecimal, 8 as BigDecimal),
                 new Item(2, 5 as BigDecimal, 100.001 as BigDecimal), // Buggy one
                 new Item(2, 3 as BigDecimal, 9 as BigDecimal)])

        repository.executeOnAllEntries(_ as Consumer<KnapsackProblem>) >> { Consumer consumer ->
            consumer.accept(problem)
        }
        when:
        packerLogic.solveAll()
        then:
        thrown(MaxItemCostException)
    }

    def 'ConstraintException when an item max weight constraint is violated'() {
        given:
        packerLogic = new DefaultPackerLogic(repository, knapsackSolver, defaultConstraint)
        KnapsackProblem problem = new KnapsackProblem(new BigDecimal("80"),
                [new Item(1, 2 as BigDecimal, 8 as BigDecimal),
                 new Item(2, 100.001 as BigDecimal, 8 as BigDecimal), // Buggy one
                 new Item(2, 3 as BigDecimal, 9 as BigDecimal)])

        repository.executeOnAllEntries(_ as Consumer<KnapsackProblem>) >> { Consumer consumer ->
            consumer.accept(problem)
        }
        when:
        packerLogic.solveAll()
        then:
        thrown(MaxItemWeightException)
    }

}
