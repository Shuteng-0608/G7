package checkInSimulation;



import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
// Implements a simple logger for logging messages to a file with different log levels.
public class Logger {
    private static final String LOG_FILE = "simulation.log";// Name of the log file.
    private static Logger instance; // only instance

    // make the constructor private
    private Logger() {
    }

    public enum LogLevel {
        INFO,// For general information messages.
        DEBUG,// For debug messages, typically for developers.
        WARN,// For warning messages, indicating potential issues.
        ERROR // For error messages, indicating failures
    }
    
    // provide the global access method for the logger
    public static synchronized Logger getInstance() {
        // Create the instance if it doesn't exist
        if (instance == null) {
            instance = new Logger();
        }
        // Return the existing or new instance.
        return instance;
    }

    // Logs a message with a specified log level.
    public static void log(LogLevel level, String message) {
    	String logEntry = LocalDateTime.now() + " [" + level + "]: " + message;
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logEntry);// Write the log entry to the file.
        } catch (IOException e) {
            // Print any IOException to the standard error stream.
            e.printStackTrace();
        }
    }
}
