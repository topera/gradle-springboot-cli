package com.ef.util

import com.ef.util.date.CommandLineDateUtil
import spock.lang.Specification

class CommandLineDateUtilSpec extends Specification {

    private def dateUtil = new CommandLineDateUtil()

    def "test with null date"() {
        expect:
        dateUtil.convertStringToDate(null) == null
    }

    def "test with blank dates"() {
        expect:
        dateUtil.convertStringToDate("") == null
        dateUtil.convertStringToDate("   ") == null
    }

    def "test with invalid dates"() {
        expect:
        dateUtil.convertStringToDate("2017-01-01 00:00:11.763") == null // date in format of log, not command-line
        dateUtil.convertStringToDate("2017-01-01.13:00") == null // missing seconds
        dateUtil.convertStringToDate("2017-01-01") == null // missing hour, minutes and seconds
    }

    def "test with valid dates"() {
        expect:
        dateUtil.convertStringToDate("2017-01-01.13:00:00") != null
        dateUtil.convertStringToDate("  2017-01-01.13:00:00  ") != null // with extra spaces
    }

    def "test details of a valid date"() {
        when: "convert a valid date"
        def date = dateUtil.convertStringToDate("2017-01-30.22:24:55")

        then: "the details are correct"
        def calendar = new GregorianCalendar()
        calendar.setTime(date)
        calendar.get(GregorianCalendar.YEAR) == 2017
        calendar.get(GregorianCalendar.MONTH) == 0 // first month of year is 0 instead of 1
        calendar.get(GregorianCalendar.DAY_OF_MONTH) == 30
        calendar.get(GregorianCalendar.HOUR_OF_DAY) == 22
        calendar.get(GregorianCalendar.MINUTE) == 24
        calendar.get(GregorianCalendar.SECOND) == 55
        calendar.get(GregorianCalendar.MILLISECOND) == 0 // should be always 0, because is never specified
    }

}
