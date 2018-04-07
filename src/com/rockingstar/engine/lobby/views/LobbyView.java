package com.rockingstar.engine.lobby.views;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.SendChallengeCommand;
import com.rockingstar.engine.game.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


import java.util.LinkedList;

public class LobbyView {

    private LinkedList<Player> _playerList;
    private LinkedList<String> _gameList;

    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screensize.getWidth();
    double height = screensize.getHeight();

    private double _iconSize;

    private int buttonCounter = 0;

    private BorderPane _lobbyPane;
    private ComboBox gameMode;
    private ComboBox gameSelectionBox;

    private String _username;

    private Button _buttonGame0;
    private Button _buttonGame1;
    private Button _buttonGame2;

    public LobbyView(LinkedList<Player> playerList, LinkedList<String> gameList) {
        _playerList = playerList;
        _gameList = gameList;

        _iconSize = 200;
    }

    public Node getNode() {
        _lobbyPane = new BorderPane();


        //top
        VBox topPane = new VBox(20);
        topPane.setPadding(new Insets(15));
        topPane.setMinHeight(150);
        topPane.setMinWidth(width);
        topPane.setAlignment(Pos.CENTER);

        Label titelLable =  new Label("Welcome " + _username);
        //Label selectGame = new Label("Please select your game");

        titelLable.setId("topText");
        //selectGame.setId("otherText");

        topPane.getChildren().addAll(titelLable);
        _lobbyPane.setTop(topPane);

        //Left
        VBox leftPane = new VBox(20);
        leftPane.setMinWidth(width/2);
        leftPane.setAlignment(Pos.CENTER);

        VBox menu = new VBox(20);
        menu.setStyle("-fx-border-color: red");
        menu.setSpacing(60);
        menu.setMaxWidth(width/4);
        menu.setMinHeight(800);
        menu.setAlignment(Pos.CENTER);

        Label gameSelectionText = new Label("Please select a game");
        gameSelectionText.setId("otherText");
        gameSelectionBox = new ComboBox();

        gameSelectionBox.getItems().addAll("Reversi", "TicTacToe", "etc" );
        Label gameModeText = new Label("How do you want to play?");
        Button selectGame = new Button("continue");
        gameModeText.setId("gameText");
        ComboBox gameMode = new ComboBox();
        gameMode.getItems().addAll("Player vs Player", "Player vs AI", "AI vs AI");


        Button goButton = new Button("Continue");


        selectGame.setOnAction(e -> {
            System.out.println("test");
            if (gameSelectionBox != null){
                menu.getChildren().clear();

                Button gameImage = new Button("");
                Label gameName = new Label("You have selected " + gameSelectionBox.getValue());
                gameName.setId("gameText");
                gameImage.setMaxWidth(200);
                gameImage.setMinHeight(200);

                if (gameSelectionBox.getValue() == "Reversi") {
                    gameImage.setId("gameImage");
                } else if (gameSelectionBox.getValue() == "TicTacToe"){
                    gameImage.setId("gameImage1");
                } else {
                    gameImage.setId("gameImage2");
                }


                menu.getChildren().addAll(gameSelectionBox,selectGame, gameName,gameModeText ,gameMode, gameImage, goButton);
            }
        });






//        gameImage.getChildren().addAll(imageLabel);
//


        menu.getChildren().addAll(gameSelectionText, gameSelectionBox, selectGame);
        leftPane.getChildren().addAll(menu);
        _lobbyPane.setLeft(leftPane);

        VBox rightPane = new VBox(20);
        rightPane.setStyle("-fx-border-color: red");
        rightPane.setMaxHeight(800);
        rightPane.setMaxWidth(width/4);
        rightPane.setAlignment(Pos.CENTER);


        VBox players = new VBox(20);
        players.setStyle("-fx-border-color: blue");
        players.setMaxWidth(width/5);
        players.setMinHeight(500);
        players.setAlignment(Pos.CENTER);
        Label onlinePLayer = new Label("List of online players");
        onlinePLayer.setId("otherText");

        rightPane.getChildren().addAll(onlinePLayer, players);
        _lobbyPane.setCenter(rightPane);


        goButton.setOnAction(e -> {
            if (gameMode.getValue() != null){
                Label gameModeSelected = new Label("You have selected: " + gameMode.getValue());
                gameModeSelected.setId("gameText");
                rightPane.getChildren().add(gameModeSelected);
                if (gameMode.getValue() == "Player vs Player"){
                    rightPane.getChildren().clear();
                    Button challenge = new Button("Challenge");
                    Button localy = new Button("Play offline");
                    rightPane.getChildren().addAll(onlinePLayer, players,gameModeSelected, challenge, localy);
                } else if(gameMode.getValue() == "Player vs AI"){
                    rightPane.getChildren().clear();
                    Label difficulty = new Label("Select your difficulty");
                    difficulty.setId("gameText");
                    Button lech = new Button("Lech Mode");
                    Button bas = new Button("Bas Mode");
                    rightPane.getChildren().addAll(gameModeSelected,difficulty,lech,bas);
                } else if(gameMode.getValue() == "AI vs AI"){
                    rightPane.getChildren().clear();
                    Label test = new Label("test");
                    test.setId("gameText");
                    rightPane.getChildren().addAll(gameModeSelected, test);
                }
            } else {
                Alert noGameModeSelected = new Alert(Alert.AlertType.INFORMATION);
                noGameModeSelected.setTitle("No game mode selecterd");
                noGameModeSelected.setHeaderText(null);
                noGameModeSelected.setContentText("ERMAHGERD!!! You did not select a game mode, please select a game mode to continue");
                noGameModeSelected.showAndWait();
            }
        });

        return _lobbyPane;

    }

    public void setPlayerList(LinkedList<Player> playerList) {
        _playerList = playerList;
    }

    public void setGameList(LinkedList<String> gameList) {
        _gameList = gameList;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public ComboBox getGameSelectionBox() {
        return gameSelectionBox;
    }

    public Button getButtonGame1() {
        return _buttonGame1;
    }

    public Button getButtonGame2() {
        return _buttonGame2;
    }
}
