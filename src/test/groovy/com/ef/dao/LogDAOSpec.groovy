package com.ef.dao

import com.ef.model.Ip
import com.ef.scanner.LogRow
import spock.lang.Specification

@SuppressWarnings("GroovyAccessibility")
class LogDAOSpec extends Specification {

    private LogDAO logDAO = new LogDAO()

    def "extractDataToPopulate: edge cases"() {
        expect:
        logDAO.extractDataToPopulate([], createExampleIpsMap()) == []
    }

    def "extractDataToPopulate: basic case"() {
        when:
        def logRow = createExampleLogRow()

        then:
        logDAO.extractDataToPopulate([logRow], createExampleIpsMap()).size() == 1
    }

    private static LogRow createExampleLogRow() {
        def logRow = new LogRow()

        logRow.setDate("2017-01-01 00:00:11.763")
        logRow.setIpAddress("example-address")
        logRow.setRequest("example-request")
        logRow.setStatus("example-status")
        logRow.setUserAgent("example-user-agent")

        return logRow
    }

    private static Map<String, Ip> createExampleIpsMap() {
        def ipsMap = [:]

        ipsMap["example-address"] = new Ip(100, "example-address")
        ipsMap["example-address-2"] = new Ip(101, "example-address-2")

        return ipsMap
    }

}
