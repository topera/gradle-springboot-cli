package com.ef.tool

import spock.lang.Specification

class ParamConverterSpec extends Specification {

    def "testing duration conversions"() {
        expect:
        new ParamConverter().convertDuration("invalid") == null
        new ParamConverter().convertDuration("daily") == Duration.DAILY
        new ParamConverter().convertDuration("DAILY") == Duration.DAILY
        new ParamConverter().convertDuration("hourly") == Duration.HOURLY
        new ParamConverter().convertDuration("HOURLY") == Duration.HOURLY
    }

    def "testing threshold conversions"() {
        expect:
        new ParamConverter().convertThreshold("invalid") == null
        new ParamConverter().convertThreshold("100") == 100
        new ParamConverter().convertThreshold("0") == 0
    }

}
