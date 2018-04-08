package com.ef.dao

import com.ef.scanner.LogRow
import spock.lang.Specification

@SuppressWarnings("GroovyAccessibility")
class IpDAOSpec extends Specification {

    private IpDAO ipDAO = new IpDAO()

    def "extractDataToPopulate: test with edge cases"() {
        expect:
        ipDAO.extractDataToPopulate(null) == []
        ipDAO.extractDataToPopulate([]) == []
    }

    def "extractDataToPopulate: test with no duplication"() {
        when:
        def logRows = createLogRows(["a", "b", "c"])

        then:
        ipDAO.extractDataToPopulate(logRows) == [["a"], ["b"], ["c"]]
    }

    def "extractDataToPopulate: test with duplications, in a sorted list"() {
        when:
        def logRows = createLogRows(["a", "a", "b", "b"])

        then:
        ipDAO.extractDataToPopulate(logRows) == [["a"], ["b"]]
    }

    def "extractDataToPopulate: test with duplications, in a unsorted list"() {
        when:
        def logRows = createLogRows(["z", "a", "b", "a", "b"])

        then:
        ipDAO.extractDataToPopulate(logRows) == [["z"], ["a"], ["b"]]
    }

    private static List<LogRow> createLogRows(List<String> ipAddresses) {
        def logRows = []

        ipAddresses.each { ipAddress ->
            logRows.add(createLogRow(ipAddress))
        }

        return logRows
    }

    private static LogRow createLogRow(String ipAddress) {
        def logRow = new LogRow()
        logRow.setIpAddress(ipAddress)
        return logRow
    }

}
