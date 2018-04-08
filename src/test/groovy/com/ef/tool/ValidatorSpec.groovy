package com.ef.tool

import spock.lang.Specification

class ValidatorSpec extends Specification {

    def "test with invalid accesslogs"() {
        when:
        validateAccesslog(null)
        then:
        thrown(ValidationException)

        when:
        validateAccesslog("   ")
        then:
        thrown(ValidationException)
    }

    def "test with valid accesslog"() {
        expect:
        validateAccesslog("access.log")
    }

    def "test with invalid startDates"() {
        when:
        validateStartDate(null)
        then:
        thrown(ValidationException)

        when:
        validateStartDate("")
        then:
        thrown(ValidationException)

        when:
        validateStartDate("2017-01-01 13:00:00")
        then:
        thrown(ValidationException)
    }

    def "test with valid startDate"() {
        expect:
        validateStartDate("2017-01-01.13:00:00")
    }

    def "test with invalid duration"() {
        when:
        validateDuration(null)
        then:
        thrown(ValidationException)

        when:
        validateDuration("wrong-value")
        then:
        thrown(ValidationException)
    }

    def "test with valid duration"() {
        expect:
        validateDuration("daily")
        validateDuration("hourly")
    }

    def "test with invalid threshold"() {
        when:
        validateThreshold(null)
        then:
        thrown(ValidationException)

        when:
        validateThreshold("0")
        then:
        thrown(ValidationException)

        when:
        validateThreshold("-100")
        then:
        thrown(ValidationException)
    }

    def "test with valid threshold"() {
        expect:
        validateThreshold("1")
        validateThreshold("100")
        validateThreshold("100000")
    }

    private static void validateAccesslog(String accesslog) {
        new Validator().validateParams(accesslog, "2017-01-01.13:00:00", "daily", "100")
    }

    private static void validateStartDate(String startDate) {
        new Validator().validateParams("access.log", startDate, "daily", "100")
    }

    private static void validateDuration(String duration) {
        new Validator().validateParams("access.log", "2017-01-01.13:00:00", duration, "100")
    }

    private static void validateThreshold(String threshold) {
        new Validator().validateParams("access.log", "2017-01-01.13:00:00", "daily", threshold)
    }

}
