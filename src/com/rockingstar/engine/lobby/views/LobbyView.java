package com.rockingstar.engine.lobby.views;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.LinkedList;

public class LobbyView {

    private LinkedList<String> _playerList;
    private LinkedList<String> _gameList;

    public LobbyView(LinkedList<String> playerList, LinkedList<String> gameList) {
        _playerList = playerList;
        _gameList = gameList;
    }

    public Node getNode() {
        return new BorderPane();
    }

    public void setPlayerList(LinkedList<String> playerList) {
        _playerList = playerList;
    }

    public void setGameList(LinkedList<String> gameList) {
        _gameList = gameList;
    }
}
