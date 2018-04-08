package com.ef.dao;

import com.ef.model.Ip;
import com.ef.scanner.LogRow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DB operations for Ip table
 */
@Component
public class IpDAO {

    private static final Logger log = LoggerFactory.getLogger(IpDAO.class);

    private static final String SQL_POPULATE = "INSERT INTO ip(ip_address) VALUES (?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ip;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void populate(List<LogRow> logRows) {
        List<Object[]> dataToPopulate = extractDataToPopulate(logRows);
        log.info("Populating ip table with " + dataToPopulate.size() + " rows...");
        jdbcTemplate.batchUpdate(SQL_POPULATE, dataToPopulate);
        log.info("Table ip populated.");
    }

    private static List<Object[]> extractDataToPopulate(List<LogRow> logRows) {
        List<String> ips = extractIps(logRows);
        List<Object[]> result = new ArrayList<>();

        ips.forEach(ip -> {
            Object[] object = new Object[1];
            object[0] = ip;
            result.add(object);
        });

        return result;
    }

    private static List<String> extractIps(List<LogRow> logRows) {
        if (logRows == null) {
            return new ArrayList<>();
        }

        List<String> ips = logRows
            .stream()
            .map(LogRow::getIpAddress) // extract ip addresses from each LogRow
            .distinct() // remove duplications
            .collect(Collectors.toList());

        log.info("Found " + ips.size() + " different ips in access.log file");
        return ips;
    }

    public Map<String, Ip> findAllIps() {
        List<Ip> ips = jdbcTemplate.query(SQL_SELECT_ALL, (rs, rowNum) -> {
            return new Ip(rs.getInt("id"), rs.getString("ip_address"));
        });

        Map<String, Ip> ipsMap = createIpsMap(ips);
        log.info("Found " + ipsMap.size() + " ips in database");
        return ipsMap;
    }

    private Map<String, Ip> createIpsMap(List<Ip> ips) {
        Map<String, Ip> resultMap = new HashMap<>();

        ips.forEach(ip -> {
            resultMap.put(ip.getIpAddress(), ip);
        });

        return resultMap;
    }

}
