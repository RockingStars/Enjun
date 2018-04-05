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

    private static Launcher _instance;

    private Launcher(GUIController guiController, ServerConnection serverConnection) {
        _guiController = guiController;
        _serverConnection = serverConnection;

        _model = new LobbyModel(this);
        _loginView = new LoginView();

        _model.addLoginActionHandlers(_loginView, this);

        // @todo Remove
        _players[1] = new Player("Klaas", Color.NAVY);
    }

    public static Launcher getInstance() {
        if (_instance == null)
            return null;

        return _instance;
    }

    public static Launcher getInstance(GUIController guiController, ServerConnection serverConnection) {
        if (_instance == null)
            _instance = new Launcher(guiController, serverConnection);

        return _instance;
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
        _players[0].login();

        _lobbyView.setUsername(_players[0].getUsername());
        _model.setPlayers(_players);

        _guiController.setCenter(_lobbyView.getNode());
        _model.addGameSelectionActionHandlers(_lobbyView);
    }

    public AbstractGame getGame() {
        return _currentGame;
    }
}
