package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.Command;

public class SubscribeCommand implements Command {

    private ServerConnection _serverConnection;
    private String _gameType;

    public SubscribeCommand(ServerConnection serverConnection, String gameType) {
        _serverConnection = serverConnection;
        _gameType = gameType;
    }

    @Override
    public void execute() {
        _serverConnection.send("subscribe " + _gameType);
    }
}
