package com.rockingstar.engine.command;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.io.models.Util;

public class LoginCommand implements Command {

    private ServerConnection _serverConnection;
    private String _username;

    public LoginCommand(ServerConnection serverConnection, String username) {
        _serverConnection = serverConnection;
        _username = username;
    }

    @Override
    public void execute() {
        String result = _serverConnection.send("login " + _username);

        switch (result.toLowerCase()) {
            case "ok":
                Util.displayStatus("Logged in as " + _username);
                break;
            case "err":
                Util.displayStatus("Unable to login");
                break;
            case "":
                Util.displayStatus("Return message requested where none was needed");
                break;
            default:
                Util.displayStatus("Unknown error message: " + result);
        }
    }
}
