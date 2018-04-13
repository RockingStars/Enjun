package com.rockingstar.engine.lobby.views;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.SendChallengeCommand;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.lobby.controllers.Launcher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class LobbyView {

    private LinkedList<Player> _playerList;
    private LinkedList<String> _gameList;
    private ScrollPane scrollPane;
    private VBox players;
    private ToggleGroup usergroup;

    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    double width = graphicsDevice.getDisplayMode().getWidth();
    double height = graphicsDevice.getDisplayMode().getHeight();

    private double _iconSize;

    public LoginView _loginView;
    private BorderPane _lobbyPane;

    private Launcher _launcher;

    private ComboBox gameSelectionBox;
    private Label gameModus;

    private String _username;
    private String _gameMode;
    private Label gameName;
    private Label gameSwitch;
    private Button gameImage;
    private Button _refreshButton;

    private RadioButton users;


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

        Label titelLable = new Label("Welcome " + _username);


        titelLable.setId("topText");

        topPane.getChildren().addAll(titelLable);
        _lobbyPane.setTop(topPane);

        //Left
        VBox leftPane = new VBox(20);
        leftPane.setMinWidth(width / 2);
        leftPane.setAlignment(Pos.CENTER);

        TextField nicknameField = new TextField();
        VBox menu = new VBox(20);
        menu.setSpacing(30);
        menu.setMaxWidth(width / 4);
        menu.setMinHeight(800);
        menu.setAlignment(Pos.CENTER);
        menu.setId("leftPane");

        gameSelectionBox = new ComboBox();
        gameSelectionBox.getItems().addAll("Reversi", "TicTacToe", "etc");

        Label gameModeText = new Label("You have selected:");
        gameModeText.setId("lobby_description");

        if (_gameMode.equals("Player")) {
            gameModus = new Label("Player vs Player");
        } else {
            gameModus = new Label("AI vs Player");
        }
        gameModus.setId("lobby_description");
        gameName = new Label("You have selected " + gameSelectionBox.getValue());
        if (gameSelectionBox.getValue() == null) {
            Label gameSelectionText = new Label("Please select a game");
            gameSelectionText.setId("lobby_description");
            menu.getChildren().addAll(gameSelectionText, gameSelectionBox, gameModeText, gameModus);
        }

        gameSelectionBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                menu.getChildren().clear();
                gameName = new Label("you have selected " + gameSelectionBox.getValue());
                gameSwitch = new Label("Do you want to switch to another game?");
                gameSwitch.setId("lobby_description");
                gameName = new Label("You have selected " + gameSelectionBox.getValue());
                gameName.setId("lobby_description");
                Button gameImage = new Button("");
                gameImage.setMaxWidth(200);
                gameImage.setMinHeight(200);
                if (gameSelectionBox.getValue() == "Reversi") {
                    gameImage.setId("gameImage");
                } else if (gameSelectionBox.getValue() == "TicTacToe") {
                    gameImage.setId("gameImage1");
                } else {
                    gameImage.setId("gameImage2");
                }
                menu.getChildren().addAll(gameName, gameSwitch, gameSelectionBox, gameModeText, gameModus, gameImage);
            }
        });

        leftPane.getChildren().addAll(menu);
        _lobbyPane.setLeft(leftPane);

        //Right
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMaxWidth(width / 5);
        scrollPane.setMaxHeight(500);
        scrollPane.setId("scrollPane");

        scrollPane.setPadding(new Insets(0, 0, 10, 0));
        VBox rightPane = new VBox(20);

        rightPane.setMaxHeight(800);
        rightPane.setMaxWidth(width / 4);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setId("rightPane");


        players = new VBox(1);
        players.setAlignment(Pos.CENTER);
        Label onlinePLayer = new Label("List of online players");
        _refreshButton = new Button("Refresh");
        onlinePLayer.setId("lobby_description");
        ListIterator iterator = (ListIterator) _playerList.iterator();
        usergroup = new ToggleGroup();

        while (iterator.hasNext()) {
            Player nextPlayer = (Player) iterator.next();
            String usernameString = nextPlayer.getUsername();
            if (usernameString.equals(_username)) {
                users = new RadioButton(usernameString + " (me)");
            } else {
                users = new RadioButton(usernameString);
            }
            users.setStyle("-fx-background-color: rgba(" + getRandom() + "," + getRandom() + "," + getRandom() + ",0.75)");

            users.setMinWidth(width / 6);
            users.setToggleGroup(usergroup);
            users.setAlignment(Pos.CENTER);
//            users.translateXProperty().bind(scrollPane.widthProperty().subtract(users.widthProperty()).divide(2));
            users.setId("users");


            players.getChildren().addAll(users);
            scrollPane.setContent(players);
        }

        rightPane.getChildren().addAll(onlinePLayer, _refreshButton, scrollPane);

        _lobbyPane.setCenter(rightPane);



        Label gameModeSelected = new Label("You have selected: " + _gameMode);
        gameModeSelected.setId("lobby_description");
        rightPane.getChildren().add(gameModeSelected);
        if (_gameMode == "Player" || _gameMode == "AI") {
            rightPane.getChildren().clear();
            Button challenge = new Button("Challenge");
            Button locally = new Button("Play offline");
            for (Toggle t : usergroup.getToggles()) {
                if (t instanceof RadioButton) {
                    ((RadioButton) t).setDisable(false);
                }
            }
            usergroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                    rightPane.getChildren().clear();
                    RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle();
                    Label selectedUser = new Label("Selected player: " + chk.getText());
                    selectedUser.setId("lobby_description");
                    if (chk.getText().equals(_username + " (me)")) {
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


                    rightPane.getChildren().addAll(onlinePLayer, _refreshButton, scrollPane, gameModeSelected, selectedUser, challenge, locally);

                }
            });


            rightPane.getChildren().addAll(onlinePLayer, _refreshButton,scrollPane, gameModeSelected, challenge, locally);


//        };

        _refreshButton.setOnAction(event -> {
            Launcher _launcher = Launcher.getInstance();
            _playerList = _launcher.getPlayerList();
            ListIterator iterator2 = (ListIterator) _playerList.iterator();
            getOnlineUser(iterator2);

        });
        }

        return _lobbyPane;

    }

    private int getRandom(){
        Random rand = new Random();
        return rand.nextInt((120)+1);
    }

    public void getOnlineUser(ListIterator iterator){
        players.getChildren().clear();
        while (iterator.hasNext()) {
            Player nextPlayer = (Player) iterator.next();
            String usernameString = nextPlayer.getUsername();
            if (usernameString.equals(_username)) {
                users = new RadioButton(usernameString + " (me)");
            } else {
                users = new RadioButton(usernameString);
            }
            users.setStyle("-fx-background-color: rgba(" + getRandom() + "," + getRandom() + "," + getRandom() + ",0.75)");

            users.setMinWidth(width / 6);
            users.setToggleGroup(usergroup);
            users.setAlignment(Pos.CENTER);
            users.setId("users");

            players.getChildren().addAll(users);
        }
    }

    public void setPlayerList(LinkedList<Player> playerList) {
        _playerList = playerList;
    }

    public void setGameList(LinkedList<String> gameList) {
        _gameList = gameList;
    }

    public void setGameMode(String gameMode) {
        _gameMode = gameMode;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public ComboBox getGameSelectionBox() {
        return gameSelectionBox;
    }

    public Button getRefreshButton() {
        return _refreshButton;
    }
}
