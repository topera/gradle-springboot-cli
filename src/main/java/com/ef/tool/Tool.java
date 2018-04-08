package com.ef.tool;

import com.ef.dao.BlacklistDAO;
import com.ef.dao.DBCheck;
import com.ef.dao.IpDAO;
import com.ef.dao.LogDAO;
import com.ef.model.Ip;
import com.ef.scanner.FileScanner;
import com.ef.scanner.LogRow;
import com.ef.util.date.CommandLineDateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Tool that reads access.log, populates database and generates statistics
 */
public class Tool {

    private static final Logger log = LoggerFactory.getLogger(Tool.class);

    private String accesslog;
    private Date startDate;
    private Duration duration;
    private Integer threshold;
    private String jdbcUrl;

    @Autowired
    private DBCheck dbCheck;

    @Autowired
    private LogDAO logDAO;

    @Autowired
    private IpDAO ipDAO;

    @Autowired
    private BlacklistDAO blacklistDAO;

    /**
     * Starts tool with specified parameters
     *
     * @param accesslog path to accesslog file
     * @param startDate start date to filter logs entries
     * @param duration  possible values: "HOURLY" or "DAILY"
     * @param threshold limit to send IP to blacklist table
     * @param jdbcUrl   OPTIONAL jdbc url to connect to a database.
     */
    public Tool(String accesslog, Date startDate, Duration duration, Integer threshold, String jdbcUrl) {
        this.accesslog = accesslog;
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
        this.jdbcUrl = jdbcUrl;
    }

    public void run() {
        printParams();
        dbCheck.testConnection();
        List<LogRow> logRows = scanFile();
        populateDatabase(logRows);
        executeBlacklistAnalysis();
    }

    private void printParams() {
        log.info("");
        log.info("Received parameters:");
        log.info("");
        log.info("> accesslog: " + accesslog);
        log.info("> startDate: " + startDate);
        log.info("> duration: " + duration);
        log.info("> threshold: " + threshold);
        log.info("> jdbc: " + jdbcUrl);
        log.info("");
    }

    private List<LogRow> scanFile() {
        List<LogRow> logRows = new FileScanner(accesslog).scan();
        log.info("Scanned " + logRows.size() + " rows.");
        return logRows;
    }

    private void populateDatabase(List<LogRow> logRows) {
        ipDAO.populate(logRows);
        logDAO.populate(logRows);
    }

    private void executeBlacklistAnalysis() {
        CommandLineDateUtil dateUtil = new CommandLineDateUtil();
        Date endDate = dateUtil.shiftDate(startDate, duration);
        Integer limitedThreshold = createLimitedThreshold(duration, threshold);
        List<Ip> blacklistedIps = logDAO.findBlacklistedIps(startDate, endDate, limitedThreshold);
        printBlacklistedIps(blacklistedIps);
        saveBlacklistedIps(blacklistedIps);
    }

    private Integer createLimitedThreshold(Duration duration, Integer threshold) {
        if (duration == Duration.DAILY) {
            return Math.min(threshold, 500);
        }
        return Math.min(threshold, 200);
    }

    private void saveBlacklistedIps(List<Ip> blacklistedIps) {
        blacklistDAO.populate(blacklistedIps, threshold);
    }

    private void printBlacklistedIps(List<Ip> blacklistedIps) {
        log.info("*******************************************");
        log.info("BEGIN: Blacklisted IPs:");
        blacklistedIps.forEach( ip -> {
            System.out.println(ip.getIpAddress());
        });
        log.info("END: Blacklisted IPs: ");
        log.info("*******************************************");
    }


}
