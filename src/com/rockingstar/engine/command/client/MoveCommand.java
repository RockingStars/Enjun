package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class MoveCommand implements Command {

    private ServerConnection _serverConnection;
    private int _move = -1;

    /**
     * Sets up the connection with the server, and sets the users move
     * @param serverConnection An instance of the ServerConnection class
     * @param move the id of the inserted move
     */
    public MoveCommand(ServerConnection serverConnection, int move) {
        _serverConnection = serverConnection;
        _move = move;
    }

    /**
     * if no move is set, move null is send to the server,
     * otherwise the inserted move is send to the server
     */
    @Override
    public void execute() {
        if(_move == -1) {
            _serverConnection.send("move null");
        } else {
            _serverConnection.send(String.format("move %s", _move));
        }
    }
}
