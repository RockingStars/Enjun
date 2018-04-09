package com.rockingstar.engine.lobby.views;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.SendChallengeCommand;
import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.GameInterface;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.lobby.controllers.Launcher;
import com.rockingstar.engine.lobby.models.LobbyModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.awt.*;



import java.util.LinkedList;
import java.util.ListIterator;

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

    RadioButton users;

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

        titelLable.setId("topText");

        topPane.getChildren().addAll(titelLable);
        _lobbyPane.setTop(topPane);

        //Left
        VBox leftPane = new VBox(20);
        leftPane.setMinWidth(width/2);
        leftPane.setAlignment(Pos.CENTER);

        VBox menu = new VBox(20);
        menu.setStyle("-fx-border-color: teal");
        menu.setSpacing(30);
        menu.setMaxWidth(width/4);
        menu.setMinHeight(800);
        menu.setAlignment(Pos.CENTER);

        Label gameSelectionText = new Label("Please select a game");
        gameSelectionText.setId("otherText");
        gameSelectionBox = new ComboBox();

        gameSelectionBox.getItems().addAll("Reversi", "TicTacToe", "etc" );
        Label gameModeText = new Label("How do you want to play?");
        Button selectGame = new Button("Select this game");
        gameModeText.setId("gameText");
        ComboBox gameMode = new ComboBox();
        gameMode.getItems().addAll("Player vs Player", "Player vs AI", "AI vs AI");


        Button goButton = new Button("Continue");


        selectGame.setOnAction(e -> {
            if (gameSelectionBox != null){
                menu.getChildren().clear();
                Label gameSwitch = new Label("Do you want to switch to another game?");
                gameSwitch.setId("gameText");
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

                menu.getChildren().addAll(gameName, gameSwitch, gameSelectionBox,selectGame,gameModeText ,gameMode, gameImage, goButton);
            }
        });


        menu.getChildren().addAll(gameSelectionText, gameSelectionBox, selectGame);
        leftPane.getChildren().addAll(menu);
        _lobbyPane.setLeft(leftPane);

        //Right
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMaxWidth(width/5);
        scrollPane.setMaxHeight(500);
        scrollPane.setId("scrollPane");

        VBox rightPane = new VBox(20);
        rightPane.setStyle("-fx-border-color: teal");
        rightPane.setMaxHeight(800);
        rightPane.setMaxWidth(width/4);
        rightPane.setAlignment(Pos.CENTER);


        VBox players = new VBox(20);
        players.setAlignment(Pos.CENTER);
        Label onlinePLayer = new Label("List of online players");
        Button refresh = new Button("Refresh");
        onlinePLayer.setId("otherText");
        ListIterator iterator = (ListIterator) _playerList.iterator();
        ToggleGroup usergroup = new ToggleGroup();

        while (iterator.hasNext()) {

            Player nextPlayer = (Player) iterator.next();
            String usernameString = nextPlayer.getUsername();
            if (usernameString.equals(_username)){
                users = new RadioButton(usernameString + " (me)");
            } else {
                users = new RadioButton(usernameString);
            }
            users.setToggleGroup(usergroup);
            users.translateXProperty().bind(scrollPane.widthProperty().subtract(users.widthProperty()).divide(2));
            users.setId("users");
            players.getChildren().addAll(users);
            scrollPane.setContent(players);
        }

        rightPane.getChildren().addAll(onlinePLayer, refresh, scrollPane);

        _lobbyPane.setCenter(rightPane);


        goButton.setOnAction(e -> {
            if (gameMode.getValue() != null){
                Label gameModeSelected = new Label("You have selected: " + gameMode.getValue());
                gameModeSelected.setId("gameText");
                rightPane.getChildren().add(gameModeSelected);
                if (gameMode.getValue() == "Player vs Player"){
                    rightPane.getChildren().clear();
                    Button challenge = new Button("Challenge");
                    Button locally = new Button("Play offline");

                    usergroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                        @Override
                        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                            rightPane.getChildren().clear();
                            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                            Label selectedUser = new Label("Selected player: " + chk.getText());
                            selectedUser.setId("gameText");
                            if (chk.getText().equals(_username + " (me)")){
                                challenge.setOnAction(event -> {
                                    Alert challengeMe = new Alert(Alert.AlertType.INFORMATION);
                                    challengeMe.setTitle("You cannot challenge yourself");
                                    challengeMe.setHeaderText(null);
                                    challengeMe.setContentText("You can not challenge yourself!, please challenge another user");
                                    challengeMe.showAndWait();
                                });
                            } else {
                                challenge.setOnAction(event -> {
                                    CommandExecutor.execute(new SendChallengeCommand(ServerConnection.getInstance(), new Player(chk.getText()), "Reversi"));
                                });
                        }

                                rightPane.getChildren().addAll(onlinePLayer, refresh, scrollPane, gameModeSelected, selectedUser, challenge, locally);

                        }
                    });

                    rightPane.getChildren().addAll(onlinePLayer, scrollPane,gameModeSelected, challenge, locally);

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


        refresh.setOnAction(event -> {
            scrollPane.setContent(null);
            ListIterator iterator2 = (ListIterator) _playerList.iterator();
            while (iterator2.hasNext()){
                Player test = (Player) iterator2.next();
                String test2 = test.getUsername();
                System.out.println(test2);
                RadioButton users = new RadioButton(test2);
                System.out.println("set content:");
                scrollPane.setContent(users);
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
