package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import checkinSys.Logger;

import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class LoggerTest {

    private static final String LOG_FILE = "simulation.log";

    @Before
    public void setUp() {
        // Ensure the log file is deleted before each test to start fresh
        File logFile = new File(LOG_FILE);
        if (logFile.exists()) {
            logFile.delete();
        }
    }

    @Test
    public void testLog() throws IOException {
        String testMessage = "This is a test log message";
        
        // Call the method under test
        Logger.log(testMessage);
        
        // Verify the log file exists
        File logFile = new File(LOG_FILE);
        assertTrue("Log file should exist after logging", logFile.exists());
        
        // Verify the content of the log file
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String lastLine = "", line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            assertTrue("The log file should contain the test message", lastLine.contains(testMessage));
        }
    }

    @After
    public void tearDown() {
        // Clean up after tests
        File logFile = new File(LOG_FILE);
        if (logFile.exists()) {
            logFile.delete();
        }
    }
}

