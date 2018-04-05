package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.Command;

public class HelpCommand implements Command {

    private ServerConnection _serverConnection;
    private String _command;

    public HelpCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    public HelpCommand(ServerConnection serverConnection, String command) {
        _serverConnection = serverConnection;
        _command = command;
    }

    @Override
    public void execute() {
        _serverConnection.send("help" + (_command != null ? " " + _command : ""));
    }
}
