package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class LogoutCommand implements Command {

    private ServerConnection _serverConnection;

    public LogoutCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    @Override
    public void execute() {
        _serverConnection.send("logout");
    }
}
