package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class ForfeitCommand implements Command {

    private ServerConnection _serverConnection;

    public ForfeitCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    @Override
    public void execute() {
        _serverConnection.send("forfeit");
    }
}
