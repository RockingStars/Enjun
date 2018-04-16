package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class SubscribeCommand implements Command {

    private ServerConnection _serverConnection;
    private String _gameType;

    /**
     * Subscribe to a game
     * @param serverConnection An instance of the ServerConnection class
     * @param gameType The type of the game the players want to subscribe to
     */
    public SubscribeCommand(ServerConnection serverConnection, String gameType) {
        _serverConnection = serverConnection;
        _gameType = gameType;
    }

    /**
     * Send the 'subscribe' command to the server
     */
    @Override
    public void execute() {
        _serverConnection.send("subscribe " + _gameType);
    }
}
