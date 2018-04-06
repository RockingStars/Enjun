package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.game.Player;

public class ChallengeCommand implements Command {

    private ServerConnection _serverConnection;

    private int _challengeNumber;

    private Player _player;
    private String _game;

    /**
     * Accept a challenge
     * @param serverConnection An instance of the ServerConnection class
     * @param challengeNumber The id of the challenge the player is accepting
     */
    public ChallengeCommand(ServerConnection serverConnection, int challengeNumber) {
        _serverConnection = serverConnection;
        _challengeNumber = challengeNumber;
    }

    /**
     * Challenge another player
     * @param serverConnection An instance of the ServerConnection class
     * @param player The player to send the invitation to
     * @param game The game the challenging player wishes to play
     */
    public ChallengeCommand(ServerConnection serverConnection, Player player, String game) {
        _serverConnection = serverConnection;
        _player = player;
        _game = game;
    }

    @Override
    public void execute() {
        if (_game != null)
            _serverConnection.send("challenge accept " + _challengeNumber);
        else
            _serverConnection.send("challenge " + _player.getUsername() + " " + _game);
    }
}
