package com.mobiquity.packer.repository

import com.mobiquity.packer.model.KnapsackProblem
import spock.lang.Specification

class RegexLineParserSpec extends Specification {

    RegexLineParser regexLineParser = new RegexLineParser();

    def "ParseException with invalid inputs"() {
        when:
        regexLineParser.parse(line)

        then:
        thrown(expectedException)

        where:
        line            || expectedException
        ""              || ParseException // Boundary case empty
        "  "            || ParseException
        null            || ParseException // Boundary case null
        "76"            || ParseException
        "76:(1,12,6)"   || ParseException // No money sign
        "76:(1.1,12,6)" || ParseException // index is decimal
        "76:(1,0,€6)" || ParseException // Weight 0
        "76:(1,-11,€6)" || ParseException // Weight minus
    }

    def "Simple with one entry: 81 : (1,53.38,€45)"() {
        when:
        KnapsackProblem knapsackProblem = regexLineParser.parse("81 : (1,53.38,€45)")
        then:
        knapsackProblem.maxWeight == new BigDecimal("81")
        knapsackProblem.items.size() == 1
        knapsackProblem.items.get(0).index == Integer.valueOf(1)
        knapsackProblem.items.get(0).weight == new BigDecimal("53.38")
        knapsackProblem.items.get(0).cost == new BigDecimal("45")
    }

    def "Test decimals with high precisions"() {
        when:
        KnapsackProblem knapsackProblem = regexLineParser.parse("100.123131232132338719379 : (1,90.21312312312321312321,€89.123123123123123)")
        then:
        knapsackProblem.maxWeight == new BigDecimal("100.123131232132338719379")
        knapsackProblem.items.size() == 1
        knapsackProblem.items.get(0).index == Integer.valueOf(1)
        knapsackProblem.items.get(0).weight == new BigDecimal("90.21312312312321312321")
        knapsackProblem.items.get(0).cost == new BigDecimal("89.123123123123123")
    }

    def "Test 3 items"() {
        when:
        KnapsackProblem knapsackProblem = regexLineParser.parse("100 : (1,4,€34)   (2,9,€55) (3,88,€9) ")
        then:
        knapsackProblem.maxWeight == new BigDecimal("100")
        knapsackProblem.items.size() == 3
        knapsackProblem.items.get(0).index == Integer.valueOf(1)
        knapsackProblem.items.get(0).weight == new BigDecimal("4")
        knapsackProblem.items.get(0).cost == new BigDecimal("34")

        knapsackProblem.items.get(1).index == Integer.valueOf(2)
        knapsackProblem.items.get(1).weight == new BigDecimal("9")
        knapsackProblem.items.get(1).cost == new BigDecimal("55")

        knapsackProblem.items.get(2).index == Integer.valueOf(3)
        knapsackProblem.items.get(2).weight == new BigDecimal("88")
        knapsackProblem.items.get(2).cost == new BigDecimal("9")
    }

}
