package com.rockingstar.engine.command;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.server.ReceivedMessageHandler;

public class CommandExecutor {

    public static void execute(Command command) {
        command.execute();
    }

    public static ReceivedMessageHandler readReply() {
        return ServerConnection.getInstance().getReply();
    }
}
