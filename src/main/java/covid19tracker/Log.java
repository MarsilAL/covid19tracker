package covid19tracker;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class Log {
    public Logger logger;

    public Log(String log_file) throws IOException {

        FileHandler fileHandler = new FileHandler(log_file, true);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);

        logger = Logger.getLogger("logger");
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);

    }
}
