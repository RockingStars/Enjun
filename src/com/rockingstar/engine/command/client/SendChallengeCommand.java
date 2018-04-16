package com.rockingstar.engine.command.client;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.game.Player;

public class SendChallengeCommand implements Command {

    private ServerConnection _serverConnection;

    private Player _player;
    private String _game;

    /**
     * Challenge another player
     * @param serverConnection An instance of the ServerConnection class
     * @param player The player to send the invitation to
     * @param game The game the challenging player wishes to play
     */
    public SendChallengeCommand(ServerConnection serverConnection, Player player, String game) {
        _serverConnection = serverConnection;
        _player = player;
        _game = game;
    }

    /**
     * Sends the command for the challenge message to the server for the selected player
     */
    @Override
    public void execute() {
        _serverConnection.send(String.format("challenge \"%s\" \"%s\"", _player.getUsername(), _game));
    }
}
