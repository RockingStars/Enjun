package com.rockingstar.engine.command.server;

import java.util.LinkedList;

public abstract class ReceivedMessageHandler {

    protected String message;

    public ReceivedMessageHandler(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    protected LinkedList<String> parseFakeCollection(String collection) {
        LinkedList<String> elements = new LinkedList<>();

        String[] unparsedElements = collection.substring(2, collection.length() -1).split(", ");

        for (String unparsedElement : unparsedElements)
            elements.add(unparsedElement.substring(1, unparsedElement.length() -1));

        return elements;
    }
}
