package com.rockingstar.engine.io.models;


import com.rockingstar.main.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author Rocking Stars
 * @version beta 1.0
 */

public class Util {
    /**
     * Displays the current state of the engine
     * @param message current state
     */
    public static void displayStatus(String message) {
        if (!Main.DEBUG_MODE)
            return;

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.printf("[%s] %s\n", dateFormat.format(new Date()), message);
    }

    /**
     * Displays the current state of the engine
     * @param message current state
     * @param success SUCCESS if successful, FAILED if not
     */
    public static void displayStatus(String message, boolean success) {
        displayStatus(message + "... [" + (success ? "SUCCESS" : "FAILED") + "]");
    }

    /**
     * Exits the Engine
     * @param message Exit message which is false
     */
    public static void exit(String message) {
        displayStatus(message, false);
        displayStatus("Exiting...");
        System.exit(-1);
    }

    /**
     * Create LinkedList from String
     * @param collection string to be added to LinkedList
     * @return Linked List containing all the Strings
     */
    public static LinkedList<String> parseFakeCollection(String collection) {
        LinkedList<String> elements = new LinkedList<>();

        String[] unparsedElements;
        boolean result = true;

        try {
            unparsedElements = collection.substring(2, collection.length() - 1).split(", ");
        }
        catch (NullPointerException e) {
            result = false;
            return elements;
        }
        finally {
            Util.displayStatus("Retrieving list", result);
        }

        for (String unparsedElement : unparsedElements)
            elements.add(unparsedElement.substring(1, unparsedElement.length() - 1));

        displayStatus("Parsing collection", elements.size() > 0);

        return elements;
    }
}
