package com.rockingstar.engine.command.server;

public abstract class ReceivedMessageHandler {

    protected String message;

    public ReceivedMessageHandler(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
