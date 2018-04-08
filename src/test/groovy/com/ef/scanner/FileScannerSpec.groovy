package com.ef.scanner

import spock.lang.Specification

class FileScannerSpec extends Specification {

    private String RESOURCES = "src/test/resources/"

    def "reading file not found"() {
        when: "reading a file not found"
        def logs = runScan("file-that-does-not-exist.log")

        then: "we receive an empty list"
        logs.size() == 0
    }

    def "reading empty file"() {
        when: "reading a empty file"
        def logs = runScan("access-empty.log")

        then: "we receive an empty list"
        logs.size() == 0
    }

    def "reading file with 1 row"() {
        when: "reading a file with 1 row"
        def logs = runScan("access-1-row.log")

        then: "we receive a list with just 1 row"
        logs.size() == 1

        and: "the values are correct"
        validateFirstRow(logs[0])
    }

    def "reading file with 2 rows"() {
        when: "reading a file with 2 rows"
        def logs = runScan("access-2-rows.log")

        then: "we receive a list just 2 rows"
        logs.size() == 2

        and: "the values are correct"
        validateFirstRow(logs[0])
        validateSecondRow(logs[1])
    }

    def "reading original file with 116484 rows"() {
        when: "reading the huge original file"
        def logs = runScan("access.log")

        then: "we receive a list with 116484 items"
        logs.size() == 116484

        and: "the values of rows 1 and 2 are correct"
        validateFirstRow(logs[0])
        validateSecondRow(logs[1])
    }

    private List<LogRow> runScan(String fileName) {
        def fileScanner = new FileScanner(RESOURCES + fileName)
        return fileScanner.scan()
    }

    private static void validateFirstRow(LogRow logRow) {
        assert logRow.date == "2017-01-01 00:00:11.763"
        assert logRow.ipAddress == "192.168.234.82"
        assert logRow.request == '"GET / HTTP/1.1"'
        assert logRow.status == "200"
        assert logRow.userAgent == '"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"'
    }

    private static void validateSecondRow(LogRow logRow) {
        assert logRow.date == "2017-01-01 00:00:21.164"
        assert logRow.ipAddress == "192.168.234.82"
        assert logRow.request == '"GET / HTTP/1.1"'
        assert logRow.status == "200"
        assert logRow.userAgent == '"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"'
    }

}
