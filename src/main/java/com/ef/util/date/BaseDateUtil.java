package com.ef.util.date;

import com.ef.tool.Duration;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Operations related to date transformation
 */
public abstract class BaseDateUtil {

    private static final Logger log = LoggerFactory.getLogger(BaseDateUtil.class);

    abstract String getDateMask();

    public Date convertStringToDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        String dateMask = getDateMask();
        try {
            return new SimpleDateFormat(dateMask).parse(dateString);
        } catch (ParseException e) {
            log.error("Error parsing date '" + dateString + "' with mask '" + dateMask + "'", e);
            return null;
        }
    }

    /**
     * Shifts the baseDate to the future, according to duration
     * @param baseDate begin date
     * @param duration daily or hourly
     * @return the date X hours in the future
     */
    public Date shiftDate(Date baseDate, Duration duration) {
        Integer hours = convertDurationToHours(duration);
        return DateUtils.addHours(baseDate, hours);
    }

    public Integer convertDurationToHours(Duration duration) {
        if (duration == Duration.DAILY){
            return 24;
        }
        return 1;
    }

}
