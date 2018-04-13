package com.rockingstar.engine.lobby.models;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.GetGameListCommand;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
import com.rockingstar.engine.lobby.views.LobbyView;
import com.rockingstar.engine.lobby.views.LoginView;
import javafx.application.Platform;

import java.util.LinkedList;

public class LobbyModel {

    private Launcher _launcher;
    private Player[] _players = new Player[2];

    public LobbyModel(Launcher launcher) {
        _launcher = launcher;
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

    public void addLoginActionHandlers(LoginView loginView ,Launcher launcher) {
        loginView.getContinueButton().setOnAction(e -> {
            if (loginView.getGamemode().equals("Player")) {
                launcher.handleLogin(String.valueOf(loginView.getInsertedUsername()), loginView.getGamemode(), false, null);
            } else {
                launcher.handleLogin(String.valueOf(loginView.getInsertedUsername()), loginView.getGamemode(), true, loginView.getDifficulty());
            }
        });
    }

//    public void addRefreshActionHandlers(LobbyView lobbyView) {
//        lobbyView.getRefreshButton().setOnAction(e ->
//                getPlayerList()
//        );}

    public void addGameSelectionActionHandlers(LobbyView lobbyView) {
        System.out.println("OK");

        lobbyView.getGameSelectionBox().setOnAction(e -> {
            Platform.runLater(() -> {
                // _launcher.loadModule(new TTTController(_players[0], _players[1]));

            });
        });
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