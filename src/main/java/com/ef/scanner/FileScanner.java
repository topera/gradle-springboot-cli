package com.ef.scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that scans access.log files and creates instances of LogRow
 */
public class FileScanner {

    private static final Logger log = LoggerFactory.getLogger(FileScanner.class);

    private static final String COLUMN_DELIMITER = "\\|";

    private String fileName;

    public FileScanner(String fileName) {
        this.fileName = fileName;
    }

    public List<LogRow> scan() {
        List<LogRow> logRows = new ArrayList<>();

        // use a Scanner to read all rows from file
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                LogRow logRow = createLogRow(nextLine);
                logRows.add(logRow);
            }
        } catch (FileNotFoundException e) {
            log.error("Cannot parse file because it was not found. File name: " + fileName, e);
        }

        return logRows;
    }

    private static LogRow createLogRow(String row) {
        // use a Scanner to extract values from all columns of a single row
        try (Scanner scanner = new Scanner(row)) {
            scanner.useDelimiter(COLUMN_DELIMITER);
            LogRow logRow = new LogRow();

            logRow.setDate(scanner.next());
            logRow.setIpAddress(scanner.next());
            logRow.setRequest(scanner.next());
            logRow.setStatus(scanner.next());
            logRow.setUserAgent(scanner.next());

            return logRow;
        }
    }

}
