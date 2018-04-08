package com.ef.util;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Creates command line options
 */
public abstract class OptionsUtil {

    private OptionsUtil(){
        // cannot be instantiated
    }

    private static final String MAIN_HELP_MESSAGE = "java -cp \"parser.jar\" com.ef.Parser --startDate=<startDate> --duration=<duration> --threshold=<threshold>";

    public static Options createOptions() {
        Options options = new Options();
        options.addOption(createAccessLog());
        options.addOption(createStartDateOption());
        options.addOption(createDurationOption());
        options.addOption(createThresholdOption());
        options.addOption(createJdbcOption());
        return options;
    }

    public static String geMainHelpMessage(){
        return MAIN_HELP_MESSAGE;
    }

    private static Option createAccessLog() {
        return Option.builder()
                .longOpt("accesslog")
                .hasArg()
                .required()
                .desc("Path to the accesslog file. Example: ../../src/test/resources/access.log")
                .argName("fileName")
                .build();
    }

    private static Option createStartDateOption() {
        return Option.builder()
                .longOpt("startDate")
                .hasArg()
                .required()
                .desc("Example: 2017-01-01.00:00:00")
                .argName("date")
                .build();
    }

    private static Option createDurationOption() {
        return Option.builder()
                .longOpt("duration")
                .hasArg()
                .desc("Duration")
                .required()
                .argName("hourly|daily")
                .build();
    }

    private static Option createThresholdOption() {
        return Option.builder()
                .longOpt("threshold")
                .hasArg()
                .desc("Threshold to send IP to blacklist")
                .required()
                .argName("integer")
                .build();
    }

    private static Option createJdbcOption() {
        return Option.builder()
                .longOpt("jdbc")
                .hasArg()
                .desc("JDBC url to connect to a database. If not informed, an embedded database will be used. Example: --jdbc=jdbc:mysql://172.17.0.2:3306/test?user=root\\&password=123456")
                .required(false)
                .argName("url")
                .build();
    }


}
