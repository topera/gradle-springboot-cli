package com.ef.util.date

import com.ef.tool.Duration
import spock.lang.Specification

@SuppressWarnings("GroovyAccessibility")
class BaseDateUtilSpec extends Specification {

    def dateUtil = new CommandLineDateUtil()

    def "shift 1 hour: simple case"() {
        when: "we shift a date to the future"
        def endDate = createShiftedDate("2017-01-01.13:00:00", Duration.HOURLY)

        then:"the date is correct"
        checkDate(endDate, 2017, 1, 1, 14, 0, 0, 0)
    }

    def "shift 1 hour: complex cases"() {
        when: "we shift a date to the future"
        def endDate = createShiftedDate("2018-10-06.15:59:59", Duration.HOURLY)
        then:"the date is correct"
        checkDate(endDate, 2018, 10, 6, 16, 59, 59, 0)

        when: "we shift a date to the future"
        endDate = createShiftedDate("2018-12-31.23:59:59", Duration.HOURLY)
        then:"the date is correct"
        checkDate(endDate, 2019, 1, 1, 00, 59, 59, 0)
    }

    def "shift 1 day: simple case"() {
        when: "we shift a date to the future"
        def endDate = createShiftedDate("2017-01-01.13:00:00", Duration.DAILY)

        then:"the date is correct"
        checkDate(endDate, 2017, 1, 2, 13, 0, 0, 0)
    }

    def "shift 1 day: complex cases"() {
        when: "we shift a date to the future"
        def endDate = createShiftedDate("2018-10-06.15:59:59", Duration.DAILY)
        then:"the date is correct"
        checkDate(endDate, 2018, 10, 7, 15, 59, 59, 0)

        when: "we shift a date to the future"
        endDate = createShiftedDate("2018-12-31.23:59:59", Duration.DAILY)
        then:"the date is correct"
        checkDate(endDate, 2019, 1, 1, 23, 59, 59, 0)
    }

    def "testing conversion of duration to hours"() {
        expect:
        dateUtil.convertDurationToHours(Duration.HOURLY) == 1
        dateUtil.convertDurationToHours(Duration.DAILY) == 24
    }

    private Date createShiftedDate(String dateString, Duration duration) {
        def beginDate = createDate(dateString)
        return dateUtil.shiftDate(beginDate, duration)
    }

    private Date createDate(String dateString) {
        return dateUtil.convertStringToDate(dateString)
    }

    private static void checkDate(Date date, int year, int month, int day, int hour, int min, int sec, int ms) {
        def calendar = new GregorianCalendar()
        calendar.setTime(date)
        assert calendar.get(GregorianCalendar.YEAR) == year
        assert calendar.get(GregorianCalendar.MONTH) == month - 1
        assert calendar.get(GregorianCalendar.DAY_OF_MONTH) == day
        assert calendar.get(GregorianCalendar.HOUR_OF_DAY) == hour
        assert calendar.get(GregorianCalendar.MINUTE) == min
        assert calendar.get(GregorianCalendar.SECOND) == sec
        assert calendar.get(GregorianCalendar.MILLISECOND) == ms
    }
}
