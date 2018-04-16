package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

/**
 * receives gamelist from server
 */
public class GetGameListCommand implements Command {

    private ServerConnection _serverConnection;

    /**
     * sets up the server connections
     * @param serverConnection An instance of the ServerConnection class
     */
    public GetGameListCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    /**
     * Send the 'get gamelist' command to the server
     */
    @Override
    public void execute() {
        _serverConnection.send("get gamelist");
    }
}
