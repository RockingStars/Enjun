package com.rockingstar.engine.lobby.views;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.AcceptChallengeCommand;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.SendChallengeCommand;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class LobbyView {

    private LinkedList<Player> _playerList;
    private LinkedList<String> _gameList;
    private ScrollPane scrollPane;
    private VBox _players;
    private ToggleGroup _usergroup;

    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    double width = graphicsDevice.getDisplayMode().getWidth();
    double height = graphicsDevice.getDisplayMode().getHeight();

    private double _iconSize;

    public LoginView _loginView;
    private BorderPane _lobbyPane;

    private Launcher _launcher;

    private Label gameModus;

    private String _selectedGame;
    private String _selectedPlayer;

    private String _username;
    private String _gameMode;
    private Label gameName;
    private Label gameSwitch;
    private Button gameImage;
    private Button _refreshButton;

    private BorderPane _leftPane;
    private BorderPane _rightPane;


    public LobbyView(Launcher launcher) {
        _playerList = new LinkedList<>();
        _gameList = new LinkedList<>();
        _launcher = launcher;

        _iconSize = 200;
    }

    public void setup() {
        _lobbyPane = new BorderPane();

        _leftPane = new BorderPane();
        _rightPane = new BorderPane();

        _leftPane.getStyleClass().add("lobby_pane");
        _rightPane.getStyleClass().add("lobby_pane");

        _lobbyPane.setLeft(_leftPane);
        _lobbyPane.setRight(_rightPane);

        _leftPane.setPadding(new Insets(50));
        _leftPane.setPrefWidth(width / 4);
        _rightPane.setPadding(new Insets(50));
        _rightPane.setPrefWidth(width / 4);

        createLeftPane();
        createRightPane();
    }

    private void createLeftPane() {
        VBox menu = new VBox();
        Label gameConfigLabel = new Label("CONFIGURATION");
        gameConfigLabel.getStyleClass().add("top_label");
        gameConfigLabel.setPrefWidth(width / 4);

        menu.setSpacing(5);
        menu.getStyleClass().add("lobby_pane_content");

        // Game selection
        Label selectGame = new Label("Select game");
        Label subscribed = new Label("Subscriptions");

        selectGame.getStyleClass().add("lobby_head");
        subscribed.getStyleClass().add("lobby_head");

        menu.getChildren().add(selectGame);
        addGames(menu, subscribed);

        Label subscribedToGame = new Label("Subscribe to this game");
        subscribedToGame.getStyleClass().add("option");

        menu.getChildren().addAll(new Label(), subscribed, subscribedToGame);

        // A hack to get the widths of each node to 100% of the vbox
        for (Node node : menu.getChildren())
            ((Label) node).setPrefWidth(Integer.MAX_VALUE);

        subscribedToGame.setOnMousePressed(e -> {
            if (_selectedGame != null)
                _launcher.subscribeToGame(_selectedGame);
        });

        // Add the previously created items to the left pane
        _leftPane.setTop(gameConfigLabel);
        _leftPane.setCenter(menu);
    }

    private void createRightPane() {
        _players = new VBox(1);
        _players.setId("online_users");

        Label onlinePlayersLabel = new Label("CHALLENGE PLAYER");
        onlinePlayersLabel.setPrefWidth(width / 4);
        onlinePlayersLabel.getStyleClass().add("top_label");

        //onlinePlayers.setSpacing(20);
        _usergroup = new ToggleGroup();
        getOnlineUsers((ListIterator) _playerList.iterator());
        addUserSelectionHandlers();

        // Add everything to the pane
        _rightPane.setTop(onlinePlayersLabel);
        _rightPane.setCenter(_players);
    }

    private void addGames(VBox menu, Label subscribed) {
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
    }

    public Node getNode() {
        return _lobbyPane;
    }

    private void getOnlineUsers(ListIterator iterator) {
        _players.getChildren().clear();
        RadioButton user;

        while (iterator.hasNext()) {
            Player nextPlayer = (Player) iterator.next();
            String usernameString = nextPlayer.getUsername();

            if (usernameString.equals(_username)) {
                user = new RadioButton(usernameString + " (me)");
                user.setDisable(true);
            }
            else {
                user = new RadioButton(usernameString);
            }

            user.setBackground(new Background(new BackgroundFill(nextPlayer.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));

            user.setPrefWidth(Integer.MAX_VALUE);
            user.setToggleGroup(_usergroup);
            user.getStyleClass().add("online_user");

            _players.getChildren().add(user);
        }
    }

    private void addUserSelectionHandlers() {
        _rightPane.getChildren().clear();

        _usergroup.selectedToggleProperty().addListener(e -> {
            if (_selectedGame == null){
                Alert selectGameFirst = new Alert(Alert.AlertType.INFORMATION);
                selectGameFirst.setTitle("select game");
                selectGameFirst.setHeaderText(null);
                selectGameFirst.setContentText("Please select a game first");
                selectGameFirst.showAndWait();
                _usergroup.selectToggle(null);
            } else {

                RadioButton chk = (RadioButton) _usergroup.getSelectedToggle();
                String playerToChallenge = chk.getText();

                if (playerToChallenge.equals(_username + " (me)")) {
                    Alert challengeMe = new Alert(Alert.AlertType.INFORMATION);

                    challengeMe.setTitle("You cannot challenge yourself");
                    challengeMe.setHeaderText(null);
                    challengeMe.setContentText("You can not challenge yourself!, please challenge another user");

                    challengeMe.showAndWait();
                } else {
                    Alert challengeInvitationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    challengeInvitationAlert.setTitle("Challenge received");
                    challengeInvitationAlert.setHeaderText(null);
                    challengeInvitationAlert.setContentText("Send an invitation for a game of " + _selectedGame + " to " + playerToChallenge + "?");

                    challengeInvitationAlert.showAndWait();

                    if (challengeInvitationAlert.getResult() == ButtonType.OK) {
                        CommandExecutor.execute(new SendChallengeCommand(ServerConnection.getInstance(), new Player(chk.getText()), _selectedGame));
                        Util.displayStatus("Sent invitation to " + playerToChallenge);
                    }
                }
            }        });
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
