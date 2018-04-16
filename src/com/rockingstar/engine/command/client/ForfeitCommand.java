package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;
/**
 * @author Rocking Stars
 * @since  beta 1.0
 */
public class ForfeitCommand implements Command {

    private ServerConnection _serverConnection;

    /**
     * sets up the server connections
     * @param serverConnection An instance of the ServerConnection class
     */
    public ForfeitCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    /**
     * Send the 'forfeit' command to the server
     */
    @Override
    public void execute() {
        _serverConnection.send("forfeit");
    }
}
