package com.ef.util.date;

/**
 * Handle dates informed in command-line
 */
public class CommandLineDateUtil extends BaseDateUtil {

    private static final String DATE_MASK = "yyyy-MM-dd.HH:mm:ss";

    @Override
    public String getDateMask() {
        return DATE_MASK;
    }

}
