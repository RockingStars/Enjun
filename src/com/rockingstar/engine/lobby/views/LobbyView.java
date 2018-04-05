package com.rockingstar.engine.lobby.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class LobbyView {

    private LinkedList<String> _playerList;
    private LinkedList<String> _gameList;

    private double _iconSize;

    private String _username;

    private Button _buttonGame0;
    private Button _buttonGame1;
    private Button _buttonGame2;

    public LobbyView(LinkedList<String> playerList, LinkedList<String> gameList) {
        _playerList = playerList;
        _gameList = gameList;

        _iconSize = 200;
    }

    public Node getNode() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(15));
        layout.setHgap(5);
        layout.setVgap(5);
        layout.setAlignment(Pos.CENTER);

        Label titleLable =  new Label("Welcome " + _username);
        titleLable.setFont(new Font(20));
        layout.add(titleLable, 0, 0);
        layout.add(new Label("Please select your game"),0,3);

        _buttonGame0 = new Button();
        Label label1 = new Label("Reversi");
        _buttonGame1 = new Button("");
        Label label2 = new Label("TicTacToe");
        _buttonGame2 = new Button();
        Label label3 = new Label("etc");

        //_buttonGame0.setStyle("-fx-background-image: url('https://lh3.googleusercontent.com/nCozPyEPkyNFZRLENJ4PqUXDKf6OuD-qcbPiyDNNT6Viw8f4XPtUCQFpns0PnWOR1Lw=w300')");
        _buttonGame0.setMinWidth(_iconSize);
        _buttonGame0.setMinHeight(_iconSize);

        //_buttonGame1.setStyle("-fx-background-image: url('https://lh3.googleusercontent.com/nCozPyEPkyNFZRLENJ4PqUXDKf6OuD-qcbPiyDNNT6Viw8f4XPtUCQFpns0PnWOR1Lw=w300')");
        _buttonGame1.setMinWidth(_iconSize);
        _buttonGame1.setMinHeight(_iconSize);

        //_buttonGame2.setStyle("-fx-background-image: url('https://lh3.googleusercontent.com/nCozPyEPkyNFZRLENJ4PqUXDKf6OuD-qcbPiyDNNT6Viw8f4XPtUCQFpns0PnWOR1Lw=w300')");
        _buttonGame2.setMinWidth(_iconSize);
        _buttonGame2.setMinHeight(_iconSize);

        layout.add(_buttonGame0,0, 15);
        layout.add(label1,0,16);
        layout.add(_buttonGame1, 1, 15);
        layout.add(label2,1,16);
        layout.add(_buttonGame2, 2, 15);
        layout.add(label3,2,16);

        return layout;
    }

    public void setPlayerList(LinkedList<String> playerList) {
        _playerList = playerList;
    }

    public void setGameList(LinkedList<String> gameList) {
        _gameList = gameList;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public Button getButtonGame0() {
        return _buttonGame0;
    }

    public Button getButtonGame1() {
        return _buttonGame1;
    }

    public Button getButtonGame2() {
        return _buttonGame2;
    }
}
