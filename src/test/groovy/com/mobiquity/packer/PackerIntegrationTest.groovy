package com.mobiquity.packer

import com.mobiquity.exception.APIException
import spock.lang.Specification

class PackerIntegrationTest extends Specification {

    def 'Run examples, it should pass the "less weight with the same value" rule'() {
        when:
        String solutions = Packer.pack(getClass().getResource('/example_input').getPath())
        then:
        "4\n-\n2,7\n8,9" == solutions
    }

    def 'Boundary test, check empty list'() {
        when:
        String solutions = Packer.pack(getClass().getResource('/empty').getPath())
        then:
        "" == solutions
    }

    def 'Input with empty line'() {
        when:
        Packer.pack(getClass().getResource('/input_with_empty_line').getPath())
        then:
        thrown(APIException)
    }

    def 'One line with solution'() {
        when:
        String solutions = Packer.pack(getClass().getResource('/one_line_with_solution').getPath())
        then:
        "4" == solutions
    }

    def 'One line with no solution'() {
        when:
        String solutions = Packer.pack(getClass().getResource('/one_line_with_no_solution').getPath())
        then:
        "-" == solutions
    }

}