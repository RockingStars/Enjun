package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class HelpCommand implements Command {

    private ServerConnection _serverConnection;
    private String _command;
    /**
     * sets up the server connections
     * @param serverConnection An instance of the ServerConnection class
     */
    public HelpCommand(ServerConnection serverConnection) {
        _serverConnection = serverConnection;
    }

    /**
     * sets up the server connections
     * @param serverConnection An instance of the ServerConnection class
     * @param command help command
     */
    public HelpCommand(ServerConnection serverConnection, String command) {
        _serverConnection = serverConnection;
        _command = command;
    }

    /**
     * sends the 'help' command to the server
     */
    @Override
    public void execute() {
        _serverConnection.send("help" + (_command != null ? " " + _command : ""));
    }
}
