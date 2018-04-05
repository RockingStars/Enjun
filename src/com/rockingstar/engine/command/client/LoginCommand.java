package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class LoginCommand implements Command {

    private ServerConnection _serverConnection;
    private String _username;

    public LoginCommand(ServerConnection serverConnection, String username) {
        _serverConnection = serverConnection;
        _username = username;
    }

    @Override
    public void execute() {
        _serverConnection.send("login " + _username);
    }
}
