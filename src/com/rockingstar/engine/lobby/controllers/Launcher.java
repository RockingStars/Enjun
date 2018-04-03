package com.rockingstar.engine.lobby.controllers;

import com.rockingstar.engine.gui.controllers.GUIController;
import com.rockingstar.engine.lobby.models.LobbyModel;
import com.rockingstar.engine.lobby.views.LobbyView;

public class Launcher {

    private GUIController _guiController;

    private LobbyModel _model;
    private LobbyView _view;

    public Launcher(GUIController guiController) {
        _guiController = guiController;

        _model = new LobbyModel();
        _view = new LobbyView(_model.getPlayerList(), _model.getGameList());
    }

    public void setCentralNode() {
        _guiController.setCenter(_view.getNode());
    }
}
