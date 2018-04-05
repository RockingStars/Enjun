package com.rockingstar.engine.lobby.models;

import com.rockingstar.engine.game.models.Player;
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

    public LinkedList<String> getPlayerList() {
        return new LinkedList<>();
    }

    public LinkedList<String> getGameList() {
        return new LinkedList<>();
    }
  
    public void addLoginActionHandlers(LoginView loginView, Launcher launcher) {
        loginView.getContinueButton().setOnAction(e -> {
            launcher.handleLogin(String.valueOf(loginView.getInsertedUsername()));
        });
    }

    public void addGameSelectionActionHandlers(LobbyView lobbyView) {
        lobbyView.getButtonGame0().setOnAction(e -> {
            _launcher.loadModule(new TTTController(_players[0], _players[1]));
        });
    }

    public void setPlayers(Player[] players) {
        _players = players;
    }
}
