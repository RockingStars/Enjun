package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;
/**
 * @author Rocking Stars
 * @since  beta 1.0
 */
public class GetPlayerListCommand implements Command {

    private ServerConnection _serverConnection;

    /**
     * Sets up the connection
     * @param serverConnection the connection to the server
     */
    public GetPlayerListCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    /**
     * Sends command to the server to receive the playerlist
     */
    @Override
    public void execute() {
        _serverConnection.send("get playerlist");
    }
}