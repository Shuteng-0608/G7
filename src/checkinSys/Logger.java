package checkinSys;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {
    private static final String LOG_FILE = "simulation.log";
    private static Logger instance; // only instance

    // make the constructor private
    private Logger() {
    }

    public enum LogLevel {
        INFO,
        DEBUG,
        WARN,
        ERROR
    }
    
    // provide the global access method for the logger
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // logger method
    public void log(LogLevel level, String message) {
    	String logEntry = LocalDateTime.now() + " [" + level + "]: " + message;
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
