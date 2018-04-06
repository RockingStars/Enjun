package com.rockingstar.engine.lobby.views;

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
import java.util.LinkedList;

public class LobbyView {

    private LinkedList<String> _playerList;
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

    public LobbyView(LinkedList<String> playerList, LinkedList<String> gameList) {
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
        gameModeText.setId("gameText");
        ComboBox gameMode = new ComboBox();
        gameMode.getItems().addAll("Player vs Player", "Player vs AI", "AI vs AI");


        Button goButton = new Button("Continue");


        gameSelectionBox.setOnAction(g -> {
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


                menu.getChildren().addAll(gameSelectionBox, gameName,gameModeText ,gameMode, gameImage, goButton);
            }
        });






//        gameImage.getChildren().addAll(imageLabel);
//


        menu.getChildren().addAll(gameSelectionText, gameSelectionBox);
        leftPane.getChildren().addAll(menu);
        _lobbyPane.setLeft(leftPane);



//        Label label1 = new Label("Reversi");
//        Label label2 = new Label("TicTacToe");
//        Label label3 = new Label("etc");
//        //Game 1
//        HBox game1 = new HBox(20);
//        _buttonGame0 = new Button();
//        Label game1Text = new Label("Reversi is a strategy board game for two players, played on an 8×8 uncheckered board. \n" + "\n"+
//                "There are sixty-four identical game pieces called disks (often spelled \"discs\"),which are light on one side and dark on the other.\n" +
//                "\n" + " Players take turns placing disks on the board with their assigned color facing up. \n" + "\n"+"" +
//                "During a play, any disks of the opponent's color that are in a straight line and bounded by the disk just placed and another disk of the current player's color are turned over to the" +
//                " current player's color.\n" +
//                "\n" +
//                "The object of the game is to have the majority of disks turned to display your color when the last playable empty square is filled.");
//        game1.getChildren().addAll(_buttonGame0, game1Text);
//
//        //Game 2
//        HBox game2 = new HBox(20);
//        _buttonGame1 = new Button("");
//        Label game2Text = new Label("Tic-tac-toe (also known as noughts and crosses or Xs and Os) is a paper-and-pencil game for two players, X and O, who take turns marking the spaces in a 3×3 grid. The player who succeeds in placing three of their marks in a horizontal, vertical, or diagonal row wins the game.");
//        game2.getChildren().addAll(_buttonGame1,game2Text);
//
//        //Game 3
//        HBox game3 = new HBox(20);
//        _buttonGame2 = new Button();
//
//        game3.getChildren().addAll(_buttonGame2,label3);
//
//        _buttonGame0.setId("gameButton0");
//        _buttonGame1.setId("gameButton1");
//        _buttonGame2.setId("gameButton2");
//
//        _buttonGame0.setMinWidth(_iconSize);
//        _buttonGame0.setMinHeight(_iconSize);
//
//        _buttonGame1.setMinWidth(_iconSize);
//        _buttonGame1.setMinHeight(_iconSize);
//
//        _buttonGame2.setMinWidth(_iconSize);
//        _buttonGame2.setMinHeight(_iconSize);
//
//
////
////        titelLable.setMaxWidth(Double.MAX_VALUE);
////        selectGame.setMaxWidth(Double.MAX_VALUE);
////        label1.setMaxWidth(Double.MAX_VALUE);
////        label2.setMaxWidth(Double.MAX_VALUE);
////        label3.setMaxWidth(Double.MAX_VALUE);
////
////        titelLable.setAlignment(Pos.CENTER);
////        selectGame.setAlignment(Pos.CENTER);
////        label1.setAlignment(Pos.CENTER);
////        label2.setAlignment(Pos.CENTER);
////        label3.setAlignment(Pos.CENTER);
//
//        label1.setId("gameText");
//        label2.setId("gameText");
//        label3.setId("gameText");
//
//        leftPane.getChildren().addAll(label1,game1,label2,game2,label3,game3);
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

    public void setPlayerList(LinkedList<String> playerList) {
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
