package de.holube.legopartsmanager.log;

public class Log {

    private Log() {
    }

    public static void debug(String message) {
        log("DEBUG: " + message);
    }

    public static void info(String message) {
        log("INFO: " + message);
    }

    public static void waring(String message) {
        log("WARNING: " + message);
    }

    public static void error(String message) {
        log("ERROR: " + message);
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
