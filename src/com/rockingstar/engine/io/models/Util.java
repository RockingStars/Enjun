package com.rockingstar.engine.io.models;

import com.rockingstar.main.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static void displayStatus(String message) {
        if (!Main.DEBUG_MODE)
            return;

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.printf("[%s] %s\n", dateFormat.format(new Date()), message);
    }

    public static void displayStatus(String message, boolean success) {
        displayStatus(message + "... [" + (success ? "SUCCESS" : "FAILED") + "]");
    }

    public static void exit(String message) {
        displayStatus(message, false);
        displayStatus("Exiting...");
        System.exit(-1);
    }
}
