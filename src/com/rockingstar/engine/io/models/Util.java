package com.rockingstar.engine.io.models;

import com.rockingstar.main.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

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

    public static LinkedList<String> parseFakeCollection(String collection) {
        LinkedList<String> elements = new LinkedList<>();

        String[] unparsedElements = collection.substring(2, collection.length() - 1).split(", ");

        for (String unparsedElement : unparsedElements)
            elements.add(unparsedElement.substring(1, unparsedElement.length() - 1));

        displayStatus("Parsing collection", elements.size() > 0);

        return elements;
    }
}
