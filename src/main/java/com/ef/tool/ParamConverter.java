package com.ef.tool;

import com.ef.util.date.BaseDateUtil;
import com.ef.util.date.CommandLineDateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Conversions of parameters
 */
public class ParamConverter {

    private static final Logger log = LoggerFactory.getLogger(ParamConverter.class);

    private BaseDateUtil commandLineDateUtil = new CommandLineDateUtil();

    public Date convertStartDate(String dateString) {
        return commandLineDateUtil.convertStringToDate(dateString);
    }

    public Duration convertDuration(String duration) {
        try {
            return Duration.valueOf(duration.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Error converting duration " + duration, e);
            return null;
        }
    }

    public Integer convertThreshold(String threshold) {
        try {
            return Integer.valueOf(threshold);
        } catch (NumberFormatException e) {
            log.error("Error converting threshold " + threshold, e);
            return null;
        }
    }


}
