package com.ef;

import com.ef.db.EmbeddedDBManager;
import com.ef.tool.Duration;
import com.ef.tool.ParamConverter;
import com.ef.tool.Tool;
import com.ef.tool.ValidationException;
import com.ef.tool.Validator;
import com.ef.util.OptionsUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

import javax.sql.DataSource;

/**
 * Class with main method, called directly from command-line
 *
 * Example of required params:
 *  --accesslog=src/test/resources/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
 *
 * Example of optional params:
 *  --jdbc=jdbc:mysql://172.17.0.2:3306/test?user=root\&password=123456
 */
@SpringBootApplication
public class Parser implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    private static final int EXIT_OK = 0;
    private static final int EXIT_ERROR = 1;

    private static Integer exitStatus; // stores the latest exit status
    private static Boolean testing = false; // inform if this class is being used in a test context

    // command line parameters
    private static String accesslog;
    private static String startDate;
    private static String duration;
    private static String threshold;
    private static String jdbcUrl;

    @Autowired
    private Tool tool;

    public static void main(String[] args) {
        exitStatus = EXIT_OK;
        try {
            CommandLine commandLine = new DefaultParser().parse(OptionsUtil.createOptions(), args);

            accesslog = commandLine.getOptionValue("accesslog");
            startDate = commandLine.getOptionValue("startDate");
            duration = commandLine.getOptionValue("duration");
            threshold = commandLine.getOptionValue("threshold");
            jdbcUrl = commandLine.getOptionValue("jdbc");

            new Validator().validateParams(accesslog, startDate, duration, threshold);

            SpringApplication.run(Parser.class, args);
        } catch (ParseException e) {
            handleCommandLineException(e);
            finishWithError();
        } catch (ValidationException e) {
            log.error("Validation error", e);
            finishWithError();
        }
    }

    @Override
    public void run(String... args) {
        try {
            tool.run();
            finishWithSuccess();
        } catch (Exception e) {
            finishWithError();
        }
    }

    @Bean
    public static Tool createTool(){
        ParamConverter paramConverter = new ParamConverter();

        Date convertedStartDate = paramConverter.convertStartDate(startDate);
        Duration convertedDuration = paramConverter.convertDuration(Parser.duration);
        Integer convertedThreshold = paramConverter.convertThreshold(threshold);

        return new Tool(accesslog, convertedStartDate, convertedDuration, convertedThreshold, jdbcUrl);
    }

    @Bean
    public DataSource dataSource() {
        // change default spring datasource from H2 to Mysql/MariaDB
        MysqlDataSource dataSource = new MysqlDataSource();
        if (jdbcUrl != null) {
            dataSource.setUrl(jdbcUrl);
        } else {
            EmbeddedDBManager.startEmbeddedDatabase();
            dataSource.setUrl(EmbeddedDBManager.JDBC_URL);
        }
        return dataSource;
    }

    private static void handleCommandLineException(ParseException e) {
        log.info("");
        log.info("Exception in command line parameters: " + e.getMessage());
        log.info("");
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(OptionsUtil.geMainHelpMessage(), OptionsUtil.createOptions());
    }

    private static void finishWithError() {
        exitStatus = EXIT_ERROR;
        if (!testing) {
            System.exit(EXIT_ERROR);
        }
    }

    private static void finishWithSuccess() {
        exitStatus = EXIT_OK;
        if (!testing) {
            System.exit(EXIT_OK);
        }
    }

    public static Boolean hasError() {
        return exitStatus == EXIT_ERROR;
    }

    public static Boolean hasSuccess() {
        return exitStatus == EXIT_OK;
    }

    public static void setTesting(Boolean testing) {
        Parser.testing = testing;
    }

}
