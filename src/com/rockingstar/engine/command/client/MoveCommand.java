package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class MoveCommand implements Command {

    private ServerConnection _serverConnection;
    private int _move = -1;

    public MoveCommand(ServerConnection serverConnection, int move) {
        _serverConnection = serverConnection;
        _move = move;
    }

    public MoveCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }


    @Override
    public void execute() {
        if(_move == -1) {
            _serverConnection.send("move null");
        } else {
            _serverConnection.send(String.format("move %s", _move));
        }
    }
}
