package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;

public class AcceptChallengeCommand implements Command {

    private ServerConnection _serverConnection;
    private int _challengeNumber;

    /**
     * Accept a challenge
     * @param serverConnection An instance of the ServerConnection class
     * @param challengeNumber The id of the challenge the player is accepting
     */
    public AcceptChallengeCommand(ServerConnection serverConnection, int challengeNumber) {
        _serverConnection = serverConnection;
        _challengeNumber = challengeNumber;
    }

    /**
     * accept the incoming challenge
     */
    @Override
    public void execute() {
        _serverConnection.send("challenge accept " + _challengeNumber);
    }
}
