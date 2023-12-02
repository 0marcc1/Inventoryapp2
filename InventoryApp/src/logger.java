import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class logger {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(logger.class.getName());

    static {
        try {
            // Create a console handler and set its level
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);

            // Create a file handler and set its level, formatter, and path
            Handler fileHandler = new FileHandler("log.txt");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());

            // Attach the handlers to the logger
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);

            // Set the logger level
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

    public static void logError(String message) {
        logger.log(Level.SEVERE, message);
    }
}
