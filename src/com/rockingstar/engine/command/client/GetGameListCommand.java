package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

/**
 * @author Rocking Stars
 * @since  beta 1.0
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
