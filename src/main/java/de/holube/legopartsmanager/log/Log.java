package de.holube.legopartsmanager.log;

public class Log {

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
