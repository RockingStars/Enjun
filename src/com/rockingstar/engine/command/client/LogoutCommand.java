package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class LogoutCommand implements Command {

    private ServerConnection _serverConnection;

    /**
     * Sets up the server connection
     * @param serverConnection An instance of the ServerConnection class
     */
    public LogoutCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    /**
     * Sends the 'logout' command to the server
     */
    @Override
    public void execute() {
        _serverConnection.send("logout");
    }
}
