package com.rockingstar.engine.lobby.controllers;

import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.models.Player;
import com.rockingstar.engine.ServerConnection;

import com.rockingstar.engine.gui.controllers.GUIController;
import com.rockingstar.engine.lobby.models.LobbyModel;
import com.rockingstar.engine.lobby.views.LobbyView;
import com.rockingstar.engine.lobby.views.LoginView;
import javafx.scene.paint.Color;

public class Launcher {

    private GUIController _guiController;

    private LobbyModel _model;
    private LobbyView _lobbyView;
    private LoginView _loginView;
    private ServerConnection _serverConnection;

    private AbstractGame _currentGame;

    private Player[] _players = new Player[2];

    public Launcher(GUIController guiController, ServerConnection serverConnection) {
        _guiController = guiController;
        _serverConnection = serverConnection;

        _model = new LobbyModel(this);
        _loginView = new LoginView();

        _model.addLoginActionHandlers(_loginView, this);

        // @todo Remove
        _players[1] = new Player("John Doe", Color.NAVY);
    }

    public void setCentralNode() {
        _guiController.setCenter(_loginView.getNode());
    }

    public void loadModule(AbstractGame game) {
        _currentGame = game;
        _guiController.setCenter(game.getView());
    }

    public void handleLogin(String username) {


        _lobbyView = new LobbyView(_model.getPlayerList(), _model.getGameList());
        _players[0] = new Player(username, new Color(0.5, 0.5, 0.5, 0));

        _lobbyView.setUsername(_players[0].getUsername());
        _model.setPlayers(_players);

        _guiController.setCenter(_lobbyView.getNode());
        _model.addGameSelectionActionHandlers(_lobbyView);
    }
}
