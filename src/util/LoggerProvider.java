package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerProvider {
    public static Logger getSimpleFormatLogger(String name, String path) {
        Logger logger = Logger.getLogger(name);
        logger.setLevel(Level.INFO);
        try {
            Handler handler = new FileHandler(path + name + ".log");
            Formatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
            logger.addHandler(handler);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return logger;
    }
}
