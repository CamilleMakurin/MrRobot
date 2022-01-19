package v2.core.log;

import java.util.Arrays;
import java.util.List;

public class Log {

    private static final List<LogLevel> logLevels = Arrays.asList(LogLevel.DEBUG, LogLevel.INFO, LogLevel.ERROR);

    public static void info(String message) {
        print(message, LogLevel.INFO);
    }

    public static void debug(String message) {
        print(message, LogLevel.DEBUG);
    }

    public static void error(String message) {
        print(message, LogLevel.ERROR);
    }

    private static void print(String message, LogLevel logLevel) {
        if (logLevels.contains(logLevel)) {
            System.out.println(message);
        }
    }

    public static void dev(String message) {
        System.out.println(message);
    }

    public static void dev(List<Object> objectList) {
        for (Object o : objectList) {
            System.out.println(o);
        }
    }

}
