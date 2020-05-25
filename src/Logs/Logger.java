package Logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger logger;

    private File logFile;
    private PrintWriter logWriter;

    private Logger() {
        logFile = new File("src//Logs//log.txt");
        try {
            logWriter = new PrintWriter(logFile);
        } catch (FileNotFoundException e) {

        }
    }

    public static Logger getInstance() {
        if (logger == null)
            logger = new Logger();
        return logger;
    }

    public void writeLog(String event, String description) {
        logWriter.println(event.toUpperCase() + ": " + description.toLowerCase() + ". AT " + getTime());
        logWriter.flush();
    }

    private String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now)).toString();
    }
}
