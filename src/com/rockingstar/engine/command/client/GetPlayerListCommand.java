package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.Command;

public class GetPlayerListCommand implements Command {

    private ServerConnection _serverConnection;

    public GetPlayerListCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    @Override
    public void execute() {
        _serverConnection.send("get playerlist");
    }
}