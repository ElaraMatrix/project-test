package logger;

import aquality.selenium.core.logging.Logger;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Log {

    private static final Logger LOGGER = Logger.getInstance();

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }
}
