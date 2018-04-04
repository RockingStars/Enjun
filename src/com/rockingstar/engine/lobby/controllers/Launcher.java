package com.rockingstar.engine.lobby.controllers;

import com.rockingstar.engine.gui.controllers.GUIController;
import com.rockingstar.engine.lobby.models.LobbyModel;
import com.rockingstar.engine.lobby.views.LobbyView;
import com.rockingstar.engine.lobby.views.LoginView;

public class Launcher {

    private GUIController _guiController;

    private LobbyModel _model;
    private LobbyView _lobbyView;
    private LoginView _loginView;

    private String _username;

    public Launcher(GUIController guiController) {
        _guiController = guiController;

        _model = new LobbyModel();
        _loginView = new LoginView();

        _model.addActionHandlers(_loginView, this);
    }

    public void setCentralNode() {
        _guiController.setCenter(_loginView.getNode());
    }

    public void handleLogin(String username) {
        _lobbyView = new LobbyView(_model.getPlayerList(), _model.getGameList());

        _username = username;
        _lobbyView.setUsername(username);

        _guiController.setCenter(_lobbyView.getNode());
    }
}
