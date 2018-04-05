package com.rockingstar.engine.io.models;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.Command;
import com.rockingstar.engine.command.server.ReceivedMessageHandler;

public class CommandExecutor {

    public static void execute(Command command) {
        command.execute();
    }

    public static ReceivedMessageHandler readReply() {
        return ServerConnection.getInstance().getReply();
    }
}
