package com.ef

import spock.lang.Specification

class ParserSpec extends Specification {

    void setup() {
        Parser.setTesting(true);
    }

    def "integration test of main, with no parameters"() {
        when: "Start application"
        Parser.main(null)

        then: "has error"
        Parser.hasError()
    }

    def "integration test of main, with wrong parameters"() {
        when: "Start application"
        def args = createCorrectArgs()
        args[0] = "--wrong-parameter=foo"
        Parser.main(args)

        then: "has error"
        Parser.hasError()
    }

    def "integration test of main, with correct parameters"() {
        when: "Start application"
        Parser.main(createCorrectArgs())

        then: "has no error"
        Parser.hasSuccess()
    }

    private static String[] createCorrectArgs() {
        def result = new String[4]
        result[0] = "--accesslog=src/test/resources/access.log"
        result[1] = "--startDate=2017-01-01.13:00:00"
        result[2] = "--duration=hourly"
        result[3] = "--threshold=100"
        return result
    }

}
