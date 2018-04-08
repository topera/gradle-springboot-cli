package com.ef.util.date;

/**
 * Handle dates informed in access.log file
 */
public class AccessLogDateUtil extends BaseDateUtil {

    private static final String DATE_MASK = "yyyy-MM-dd HH:mm:ss.SSS";

    @Override
    public String getDateMask() {
        return DATE_MASK;
    }

}
