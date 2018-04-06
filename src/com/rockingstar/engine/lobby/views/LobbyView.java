package com.rockingstar.engine.lobby.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
        BorderPane borderPane = new BorderPane();
        GridPane layout = new GridPane();
        borderPane.setCenter(layout);

        layout.setPadding(new Insets(15));
        layout.setHgap(5);
        layout.setVgap(5);
        layout.setAlignment(Pos.CENTER);


        Label titelLable =  new Label("Welcome " + _username);
        layout.add(titelLable, 0, 0,3,1);
        Label selectGame = new Label("Please select your game");
        layout.add(selectGame,0,3,3,1);

        titelLable.setId("topText");
        selectGame.setId("otherText");

        _buttonGame0 = new Button();
        Label label1 = new Label("Reversi");
        _buttonGame1 = new Button();
        Label label2 = new Label("TicTacToe");
        _buttonGame2 = new Button();
        Label label3 = new Label("etc");

        _buttonGame0.setId("gameButton0");
        _buttonGame1.setId("gameButton1");
        _buttonGame2.setId("gameButton2");

        _buttonGame0.setMinWidth(_iconSize);
        _buttonGame0.setMinHeight(_iconSize);

        _buttonGame1.setMinWidth(_iconSize);
        _buttonGame1.setMinHeight(_iconSize);

        _buttonGame2.setMinWidth(_iconSize);
        _buttonGame2.setMinHeight(_iconSize);

        layout.add(_buttonGame0,0, 15);
        layout.add(label1,0,16);
        layout.add(_buttonGame1, 1, 15);
        layout.add(label2,1,16);
        layout.add(_buttonGame2, 2, 15);
        layout.add(label3,2,16);

        titelLable.setMaxWidth(Double.MAX_VALUE);
        selectGame.setMaxWidth(Double.MAX_VALUE);
        label1.setMaxWidth(Double.MAX_VALUE);
        label2.setMaxWidth(Double.MAX_VALUE);
        label3.setMaxWidth(Double.MAX_VALUE);

        titelLable.setAlignment(Pos.CENTER);
        selectGame.setAlignment(Pos.CENTER);
        label1.setAlignment(Pos.CENTER);
        label2.setAlignment(Pos.CENTER);
        label3.setAlignment(Pos.CENTER);

        label1.setId("gameText");
        label2.setId("gameText");
        label3.setId("gameText");

        VBox vbox = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportWidth(200);
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        vbox.getChildren().add(scrollPane);

        borderPane.setRight(vbox);
        borderPane.setCenter(layout);

        return borderPane;
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
