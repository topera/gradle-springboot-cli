package com.ef.util

import spock.lang.Specification

class OptionsUtilSpec extends Specification {

    def "check if options are created"() {
        when: "options are created"
        def options = OptionsUtil.createOptions()

        then: "all 4 operations are there"
        options.hasOption("accesslog")
        options.hasOption("startDate")
        options.hasOption("duration")
        options.hasOption("threshold")
    }

    def "check if main help message is there"() {
        expect:
        OptionsUtil.geMainHelpMessage().size() > 10
    }

}
