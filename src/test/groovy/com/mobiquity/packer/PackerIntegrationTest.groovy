package com.mobiquity.packer

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

}