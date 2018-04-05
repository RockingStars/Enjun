package com.rockingstar.engine.lobby.models;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.lobby.controllers.Launcher;
import com.rockingstar.engine.lobby.views.LoginView;

import java.util.LinkedList;

public class LobbyModel {

    public LinkedList<String> getPlayerList() {
        return new LinkedList<>();
    }

    public LinkedList<String> getGameList() {
        return new LinkedList<>();
    }

    public void addActionHandlers(LoginView loginView, Launcher launcher) {
        loginView.getContinueButton().setOnAction(e -> {
                ServerConnection.getInstance().send("login "+ loginView.getInsertedUsername());
                launcher.handleLogin(String.valueOf(loginView.getInsertedUsername()));
        });
    }
}
