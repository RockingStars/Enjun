package com.rockingstar.engine.command.server;

public class ReturnCodeHandler extends ReceivedMessageHandler {

    public ReturnCodeHandler(String message) {
        super(message);
    }

    public String getReturnCode() {
        return message;
    }
}
