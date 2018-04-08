package com.ef.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfiguration;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;

/**
 * Handle the embedded (MariaDB) database
 */
public class EmbeddedDBManager {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedDBManager.class);

    private static final Integer PORT = 3307;
    public static final String JDBC_URL = "jdbc:mysql://localhost:" + PORT + "/test?user=root&password=";

    private static Boolean databaseStarted = false; // flag to avoid problems with tests called in sequence

    public static void startEmbeddedDatabase() {
        if (!databaseStarted) {
            log.info("Creating embedded database...");
            try {
                DB db = DB.newEmbeddedDB(createDBConfiguration());
                db.start();
            } catch (ManagedProcessException e) {
                log.error("Error creating embedded DB.", e);
            }
            databaseStarted = true;
        }
    }

    private static DBConfiguration createDBConfiguration() {
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(PORT);
        return configBuilder.build();
    }

}
