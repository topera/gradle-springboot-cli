package com.ef.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Check database access
 */
@Component
public class DBCheck {

    private static final Logger log = LoggerFactory.getLogger(DBCheck.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void testConnection() {
        log.info("Testing connection with database");
        try {
            printConnectionDetails();
        } catch (SQLException e) {
            log.error("Connection Failed!", e);
        }
    }

    private void printConnectionDetails() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            log.info("");
            log.info("Connection details:");
            log.info("");
            log.info("> userName: " + metaData.getUserName());
            log.info("> URL: " + metaData.getURL());
            log.info("> DatabaseProductName: " + metaData.getDatabaseProductName());
            log.info("> Version: " + metaData.getDatabaseProductVersion());
            log.info("");
        }
    }

}
