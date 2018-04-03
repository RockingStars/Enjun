package com.rockingstar.engine.lobby.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.LinkedList;

public class LobbyView {

    private LinkedList<String> _playerList;
    private LinkedList<String> _gameList;

    private double _iconSize;

    private String _username;

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

        Button buttonGame1 = new Button();
        Label label1 = new Label("Reversi");
        Button buttonGame2 = new Button("");
        Label label2 = new Label("TicTacToe");
        Button buttonGame3 = new Button();
        Label label3 = new Label("etc");

        buttonGame1.setStyle("-fx-background-image: url('https://lh3.googleusercontent.com/nCozPyEPkyNFZRLENJ4PqUXDKf6OuD-qcbPiyDNNT6Viw8f4XPtUCQFpns0PnWOR1Lw=w300')");
        buttonGame1.setMinWidth(_iconSize);
        buttonGame1.setMinHeight(_iconSize);

        buttonGame2.setStyle("-fx-background-image: url('https://lh3.googleusercontent.com/nCozPyEPkyNFZRLENJ4PqUXDKf6OuD-qcbPiyDNNT6Viw8f4XPtUCQFpns0PnWOR1Lw=w300')");
        buttonGame2.setMinWidth(_iconSize);
        buttonGame2.setMinHeight(_iconSize);

        buttonGame3.setStyle("-fx-background-image: url('https://lh3.googleusercontent.com/nCozPyEPkyNFZRLENJ4PqUXDKf6OuD-qcbPiyDNNT6Viw8f4XPtUCQFpns0PnWOR1Lw=w300')");
        buttonGame3.setMinWidth(_iconSize);
        buttonGame3.setMinHeight(_iconSize);

        layout.add(buttonGame1,0, 15);
        layout.add(label1,0,16);
        layout.add(buttonGame2, 1, 15);
        layout.add(label2,1,16);
        layout.add(buttonGame3, 2, 15);
        layout.add(label3,2,16);

        buttonGame1.setOnAction(e -> {
            String getValue = String.valueOf(label1.getText());
            System.out.println(getValue);
        });

        buttonGame2.setOnAction(e -> {
            String getValue = String.valueOf(label2.getText());
            System.out.println(getValue);
        });

        buttonGame3.setOnAction(e -> {
            String getValue = String.valueOf(label3.getText());
            System.out.println(getValue);
        });

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
}
