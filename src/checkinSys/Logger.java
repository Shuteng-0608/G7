package checkinSys;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
	
    private static final String LOG_FILE = "simulation.log";

    public static void log(String message) {
        String logEntry = LocalDateTime.now() + ": " + message;
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
