package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class MoveCommand implements Command {

    private ServerConnection _serverConnection;
    private int _move;

    public MoveCommand(ServerConnection serverConnection, int move) {
        _serverConnection = serverConnection;
        _move = move;
    }

    @Override
    public void execute() {
        _serverConnection.send("move " + _move);
    }
}
