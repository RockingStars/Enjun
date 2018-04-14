package com.rockingstar.engine.lobby.views;

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

    private Label gameModus;

    private String _selectedGame;

    private String _username;
    private String _gameMode;
    private Label gameName;
    private Label gameSwitch;
    private Button gameImage;
    private Button _refreshButton;

    private RadioButton users;

    private BorderPane _leftPane;
    private BorderPane _rightPane;


    public LobbyView(LinkedList<Player> playerList, LinkedList<String> gameList) {
        _playerList = playerList;
        _gameList = gameList;

        _iconSize = 200;

        setup();
    }

    public void setup() {
        _lobbyPane = new BorderPane();

        _leftPane = new BorderPane();
        _rightPane = new BorderPane();

        _leftPane.setId("lobby_pane");
        _rightPane.setId("lobby_pane");

        _lobbyPane.setLeft(_leftPane);
        _lobbyPane.setRight(_rightPane);

        _leftPane.setPadding(new Insets(50));
        _leftPane.setPrefWidth(width / 4);
        _rightPane.setPadding(new Insets(50));
        _leftPane.setPrefWidth(width / 4);
    }

    public Node getNode() {
        //Left pane
        VBox menu = new VBox();
        Label gameConfigLabel = new Label("CONFIGURATION");
        gameConfigLabel.setId("top_label");
        gameConfigLabel.setPrefWidth(width / 4);

        menu.setSpacing(5);
        menu.setId("lobby_pane_content");

        // Add the previously created items to the left pane
        _leftPane.setTop(gameConfigLabel);
        _leftPane.setCenter(menu);

        // Game selection
        Label selectGame = new Label("Select game");
        Label subscribed = new Label("Subscribe to game");

        selectGame.setId("lobby_head");
        subscribed.setId("lobby_head");

        menu.getChildren().add(selectGame);

        for (String game : _gameList) {
            Label label = new Label(game);
            label.getStyleClass().add("option");

            label.setOnMousePressed(e -> {
                _selectedGame = game;

                for (Node node : menu.getChildren()) {
                    if (node == subscribed)
                        break;

                    node.getStyleClass().remove("option_selected");
                }

                label.getStyleClass().add("option_selected");
            });

            label.setPrefWidth(Integer.MAX_VALUE);
            menu.getChildren().add(label);
        }

        Label subscribeTrue = new Label("Yes");
        Label subscribeFalse = new Label("No");

        subscribeTrue.getStyleClass().add("option");
        subscribeFalse.getStyleClass().addAll("option", "option_selected");

        menu.getChildren().addAll(new Label(), subscribed, subscribeTrue, subscribeFalse);

        // A hack to get the widths of each node to 100% of the vbox
        selectGame.setPrefWidth(Integer.MAX_VALUE);
        subscribed.setPrefWidth(Integer.MAX_VALUE);
        subscribeTrue.setPrefWidth(Integer.MAX_VALUE);
        subscribeFalse.setPrefWidth(Integer.MAX_VALUE);

        // Event handlers
        subscribeFalse.setOnMousePressed(e -> {
            subscribeTrue.getStyleClass().remove("option_selected");
            subscribeFalse.getStyleClass().add("option_selected");
        });

        subscribeTrue.setOnMousePressed(e -> {
            subscribeTrue.getStyleClass().add("option_selected");
            subscribeFalse.getStyleClass().remove("option_selected");
        });

        // Right pane
        players = new VBox(1);
        players.setAlignment(Pos.CENTER);

        Label onlinePlayersLabel = new Label("ONLINE PLAYERS");
        onlinePlayersLabel.setId("top_label");

        VBox onlinePlayers = new VBox();
        onlinePlayers.setId("lobby_pane_content");

        onlinePlayers.setSpacing(20);
        _rightPane.setTop(onlinePlayersLabel);
        _rightPane.setCenter(onlinePlayers);
/*
        _refreshButton = new Button("Refresh");
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
/*
        _rightPane.getChildren().addAll(onlinePLayer, _refreshButton, scrollPane);

        Label gameModeSelected = new Label("You have selected: " + _gameMode);
        gameModeSelected.setId("lobby_description");
        _rightPane.getChildren().add(gameModeSelected);
        if (_gameMode.equals("Player") || _gameMode.equals("AI")) {
            _rightPane.getChildren().clear();
            Button challenge = new Button("Challenge");
            for (Toggle t : usergroup.getToggles()) {
                if (t instanceof RadioButton) {
                    ((RadioButton) t).setDisable(false);
                }
            }
            usergroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                    _rightPane.getChildren().clear();
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


                    _rightPane.getChildren().addAll(onlinePLayer, _refreshButton, scrollPane, gameModeSelected, selectedUser, challenge);
                }
            });

            _rightPane.getChildren().addAll(onlinePLayer, _refreshButton,scrollPane, gameModeSelected, challenge);


//        };

            _refreshButton.setOnAction(event -> {
                Launcher _launcher = Launcher.getInstance();
                _playerList = _launcher.getPlayerList();
                ListIterator iterator2 = (ListIterator) _playerList.iterator();
                getOnlineUser(iterator2);
            });
        }*/

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

    public Button getRefreshButton() {
        return _refreshButton;
    }
}
