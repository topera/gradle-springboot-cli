package com.ef.tool;

/**
 * Validation of all parameters
 */
public class Validator {

    private ParamConverter paramConverter = new ParamConverter();

    public void validateParams(String accesslog, String startDate, String duration, String threshold) throws ValidationException {
        validateAccesslog(accesslog);
        validateStartDate(startDate);
        validateDuration(duration);
        validateThreshold(threshold);
    }

    private void validateAccesslog(String accesslog) throws ValidationException {
        if (accesslog == null || accesslog.trim().isEmpty()) {
            throw new ValidationException("Param 'accesslog' cannot be blank.");
        }
    }

    private void validateStartDate(String startDate) throws ValidationException {
        if (startDate == null || startDate.trim().isEmpty()) {
            throw new ValidationException("Param 'startDate' cannot be blank.");
        } else if (paramConverter.convertStartDate(startDate) == null) {
            throw new ValidationException("Param 'startDate' has invalid date: " + startDate);
        }
    }

    private void validateDuration(String duration) throws ValidationException {
        if (duration == null) {
            throw new ValidationException("Param 'duration' cannot be null.");
        } else if (paramConverter.convertDuration(duration) == null) {
            throw new ValidationException("Param 'duration' is invalid: '" + duration + "'");
        }
    }

    private void validateThreshold(String threshold) throws ValidationException {
        if (threshold == null) {
            throw new ValidationException("Param 'threshold' cannot be null.");
        }

        Integer thresholdInt = paramConverter.convertThreshold(threshold);
        if (thresholdInt == null || thresholdInt <= 0) {
            throw new ValidationException("Param 'threshold' has invalid value: '" + threshold + "'");
        }
    }

}
