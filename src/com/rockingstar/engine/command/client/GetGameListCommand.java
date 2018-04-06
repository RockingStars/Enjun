package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class GetGameListCommand implements Command {

    private ServerConnection _serverConnection;

    public GetGameListCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    @Override
    public void execute() {
        _serverConnection.send("get gamelist");
    }
}
