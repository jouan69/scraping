package perso.scraping.logs;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by Erwan on 16/02/2018.
 */
public class MyLogger {

    private static FileHandler fileHTML;
    private static Formatter formatterHTML;

    // get the global logger to configure it
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void setup() throws IOException {

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileHTML = new FileHandler("Logging.html");

        // create an HTML formatter
        formatterHTML = new MyHtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }

    public void log(Level level, String s, Object[] obj) {
        logger.log(level, s, obj);
    }
}
