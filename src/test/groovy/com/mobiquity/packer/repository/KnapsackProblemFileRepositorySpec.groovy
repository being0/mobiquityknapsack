package com.mobiquity.packer.repository


import com.mobiquity.packer.model.KnapsackProblem
import spock.lang.Specification

class KnapsackProblemFileRepositorySpec extends Specification {

    KnapsackProblemFileRepository repository;
    LineParser lineParser;

    def setup() {
        lineParser = Stub(LineParser)
    }


    def 'Boundary case: test an empty file, repository should return empty list'() {
        given:
        repository = new KnapsackProblemFileRepository(getClass().getResource('/empty').getPath(), lineParser)
        when:
        List<KnapsackProblem> items = repository.readAll()
        then:
        items.size() == 0
    }

    def 'ParseException when file is not there'() {
        given:
        repository = new KnapsackProblemFileRepository("/notExistsPath", lineParser)
        when:
        repository.readAll()
        then:
        thrown(ParseException)
    }

    def 'File with 3 items'() {
        given:
        repository = new KnapsackProblemFileRepository(getClass().getResource('/three_problems').getPath(), lineParser)
        lineParser.parse() >> Arrays.asList(new KnapsackProblem(new BigDecimal("100"), Collections.emptyList()))
        when:
        List<KnapsackProblem> items = repository.readAll()
        then:
        items.size() == 3
    }
}
