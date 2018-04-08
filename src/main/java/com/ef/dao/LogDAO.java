package com.ef.dao;

import com.ef.model.Ip;
import com.ef.scanner.LogRow;
import com.ef.util.date.AccessLogDateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DB operations for Log table
 */
@Component
public class LogDAO {

    private static final Logger log = LoggerFactory.getLogger(LogDAO.class);

    private static final String SQL_POPULATE = "INSERT INTO log(ip_id, date, request, status, userAgent) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_BLACKLIST = "" +
            "SELECT t.id, t.ip_address FROM ( " +
            "  SELECT " +
            "    ip.id, ip.ip_address, count(ip.id) requests" +
            "  FROM " +
            "    log, ip " +
            "  WHERE " +
            "    log.ip_id = ip.id " +
            "    AND log.date BETWEEN ? AND ? " +
            "  GROUP BY log.ip_id " +
            ") as t WHERE requests > ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IpDAO ipDAO;

    public void populate(List<LogRow> logRows) {
        log.info("Populating log table with " + logRows.size() + " rows...");
        Map<String, Ip> ipsMap = ipDAO.findAllIps();
        List<Object[]> dataToPopulate = extractDataToPopulate(logRows, ipsMap);
        jdbcTemplate.batchUpdate(SQL_POPULATE, dataToPopulate);
        log.info("Population complete.");
    }

    private List<Object[]> extractDataToPopulate(List<LogRow> logRows, Map<String, Ip> ipsMap) {
        AccessLogDateUtil accessLogDateUtil = new AccessLogDateUtil();
        List<Object[]> result = new ArrayList<>();

        logRows.forEach( logRow -> {
            Object[] object = new Object[5];
            object[0] = ipsMap.get(logRow.getIpAddress()).getId();
            object[1] = accessLogDateUtil.convertStringToDate(logRow.getDate());
            object[2] = logRow.getRequest();
            object[3] = logRow.getStatus();
            object[4] = logRow.getUserAgent();

            result.add(object);
        });

        return result;
    }

    public List<Ip> findBlacklistedIps(Date beginDate, Date endDate, Integer threshold) {
        log.info("");
        log.info("Finding Ips in with following parameters:");
        log.info("> beginDate: " + beginDate);
        log.info("> endDate: " + endDate);
        log.info("> threshold: " + threshold);

        Object[] params = new Object[3];
        params[0] = beginDate;
        params[1] = endDate;
        params[2] = threshold;
        List<Ip> ips = jdbcTemplate.query(SQL_BLACKLIST, params, (rs, rowNum) -> {
            return new Ip(rs.getInt("id"), rs.getString("ip_address"));
        });

        log.info("Found " + ips.size() + " IPs to be blacklisted in blacklist table.");
        return ips;
    }
}
