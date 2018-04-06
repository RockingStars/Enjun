package com.rockingstar.engine.lobby.models;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.GetGameListCommand;
import com.rockingstar.engine.command.client.GetPlayerListCommand;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
import com.rockingstar.engine.lobby.views.LobbyView;
import com.rockingstar.engine.lobby.views.LoginView;
import com.rockingstar.modules.TicTacToe.controllers.TTTController;

import java.util.LinkedList;

public class LobbyModel {

    private Launcher _launcher;
    private Player[] _players = new Player[2];

    public LobbyModel(Launcher launcher) {
        _launcher = launcher;
    }

    public LinkedList<Player> getPlayerList() {
        LinkedList<Player> players = new LinkedList<>();

        ServerConnection serverConnection = ServerConnection.getInstance();
        CommandExecutor.execute(new GetPlayerListCommand(serverConnection));

        if (serverConnection.isValidCommand())
            for (String player : Util.parseFakeCollection(serverConnection.getResponse()))
                players.add(new Player(player));
        else
            Util.displayStatus("Loading player list", false);

        return players;
    }

    public LinkedList<String> getGameList() {
        ServerConnection serverConnection = ServerConnection.getInstance();
        CommandExecutor.execute(new GetGameListCommand(serverConnection));

        if (serverConnection.isValidCommand())
            return Util.parseFakeCollection(serverConnection.getResponse());
        else
            Util.displayStatus("Loading player list", false);

        return new LinkedList<>();
    }
  
    public void addLoginActionHandlers(LoginView loginView, Launcher launcher) {
        loginView.getContinueButton().setOnAction(e ->
            launcher.handleLogin(String.valueOf(loginView.getInsertedUsername())));
    }

    public void addGameSelectionActionHandlers(LobbyView lobbyView) {
        lobbyView.getButtonGame0().setOnAction(e ->
            _launcher.loadModule(new TTTController(_players[0], _players[1])));
    }

    public void setPlayers(Player[] players) {
        _players = players;
    }

    public void setLocalPlayer(Player player) {
        _players[0] = player;
    }

    public void setOpponent(Player player) {
        _players[1] = player;
    }
}
