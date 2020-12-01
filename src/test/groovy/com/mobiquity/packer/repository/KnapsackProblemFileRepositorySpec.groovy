package com.mobiquity.packer.repository

import com.mobiquity.packer.model.KnapsackProblem
import spock.lang.Specification

class KnapsackProblemFileRepositorySpec extends Specification {

    KnapsackProblemFileRepository repository
    ProblemParser lineParser

    def setup() {
        lineParser = Stub(ProblemParser)
    }


    def 'readAll:Boundary case: test an empty file, repository should return empty list'() {
        given:
        repository = new KnapsackProblemFileRepository(getClass().getResource('/empty').getPath(), lineParser)
        when:
        List<KnapsackProblem> items = repository.readAll()
        then:
        items.size() == 0
    }

    def 'readAll:RepositoryException when file is not there'() {
        given:
        repository = new KnapsackProblemFileRepository("/notExistsPath", lineParser)
        when:
        repository.readAll()
        then:
        thrown(RepositoryException)
    }

    def 'readAll:File with 3 items'() {
        given:
        repository = new KnapsackProblemFileRepository(getClass().getResource('/three_problems').getPath(), lineParser)
        lineParser.parse() >> Arrays.asList(new KnapsackProblem(new BigDecimal("100"), Collections.emptyList()))
        when:
        List<KnapsackProblem> items = repository.readAll()
        then:
        items.size() == 3
    }

    def 'executeOnAllEntries:Boundary case: test an empty file, repository should return empty list'() {
        given:
        repository = new KnapsackProblemFileRepository(getClass().getResource('/empty').getPath(), lineParser)
        when:
        int counter = 0;
        repository.executeOnAllEntries({ problem -> counter++ })
        then:
        counter == 0
    }

    def 'executeOnAllEntries:RepositoryException when file is not there'() {
        given:
        repository = new KnapsackProblemFileRepository("/notExistsPath", lineParser)
        when:
        repository.executeOnAllEntries({ problem -> problem.items })
        then:
        thrown(RepositoryException)
    }

    def 'executeOnAllEntries:File with 3 items'() {
        given:
        repository = new KnapsackProblemFileRepository(getClass().getResource('/three_problems').getPath(), lineParser)
        lineParser.parse() >> Arrays.asList(new KnapsackProblem(new BigDecimal("100"), Collections.emptyList()))
        when:
        int counter = 0;
        repository.executeOnAllEntries({ problem -> counter++ })
        then:
        counter == 3
    }
}
