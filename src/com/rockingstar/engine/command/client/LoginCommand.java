package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class LoginCommand implements Command {

    private ServerConnection _serverConnection;
    private String _username;
    /**
     * sets up the server connections and username
     * @param serverConnection An instance of the ServerConnection class
     * @param username The inserted username of the player
     */
    public LoginCommand(ServerConnection serverConnection, String username) {
        _serverConnection = serverConnection;
        _username = username;
    }

    /**
     * Send the 'login' command to the server
     */
    @Override
    public void execute() {
        _serverConnection.send("login " + _username);
    }
}
