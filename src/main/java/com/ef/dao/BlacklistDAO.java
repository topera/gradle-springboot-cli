package com.ef.dao;

import com.ef.model.Ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * DB operations for Blacklist table
 */
@Component
public class BlacklistDAO {

    private static final Logger log = LoggerFactory.getLogger(BlacklistDAO.class);

    private static final String SQL_POPULATE = "INSERT INTO blacklist(ip_id, comment) VALUES (?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void populate(List<Ip> blacklistedIps, Integer threshold) {
        List<Object[]> dataToPopulate = extractDataToPopulate(blacklistedIps, threshold);
        log.info("Populating blacklist table with " + dataToPopulate.size() + " rows...");
        jdbcTemplate.batchUpdate(SQL_POPULATE, dataToPopulate);
        log.info("Table blacklist populated.");
    }

    private static List<Object[]> extractDataToPopulate(List<Ip> blacklistedIps, Integer threshold) {
        List<Object[]> result = new ArrayList<>();
        String comment = "Blacklisted because did more than " + threshold + " requests.";

        blacklistedIps.forEach(ip -> {
            Object[] object = new Object[2];
            object[0] = ip.getId();
            object[1] = comment;
            result.add(object);
        });

        return result;
    }

}
