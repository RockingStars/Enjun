package com.rockingstar.engine.lobby.views;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.SendChallengeCommand;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class LobbyView {

    private LinkedList<Player> _playerList;
    private LinkedList<String> _gameList;
    private VBox _players;
    private ToggleGroup _usergroup;

    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    double width = graphicsDevice.getDisplayMode().getWidth();
    double height = graphicsDevice.getDisplayMode().getHeight();

    private BorderPane _lobbyPane;

    private Launcher _launcher;

    private Label gameModus;

    private String _selectedGame;

    private String _username;
    private String _gameMode;

    private BorderPane _leftPane;
    private BorderPane _rightPane;
    private VBox _rightPaneContentBox;

    private Label _subscribed;
    private VBox _leftPaneMenu;

    private Label _onlinePlayersLabel;

    private static final int NUMBER_OF_PLAYERS_WITHOUT_SCROLL = 15;

    public LobbyView(Launcher launcher) {
        _playerList = new LinkedList<>();
        _gameList = new LinkedList<>();
        _launcher = launcher;
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
        _leftPaneMenu = new VBox();
        Label gameConfigLabel = new Label("CONFIGURATION");
        gameConfigLabel.getStyleClass().add("top_label");
        gameConfigLabel.setPrefWidth(width / 4);

        _leftPaneMenu.setSpacing(5);
        _leftPaneMenu.getStyleClass().add("lobby_pane_content");

        // Add the previously created items to the left pane
        _leftPane.setTop(gameConfigLabel);
        _leftPane.setCenter(_leftPaneMenu);
    }

    private void populateLeftPane() {
        Label selectGame = new Label("Select game");
        _subscribed = new Label("Subscriptions");

        selectGame.getStyleClass().add("lobby_head");
        _subscribed.getStyleClass().add("lobby_head");

        _leftPaneMenu.getChildren().add(selectGame);
        addGames();

        Label subscribedToGame = new Label("Subscribe to this game");
        subscribedToGame.getStyleClass().add("option");

        _leftPaneMenu.getChildren().addAll(new Label(), _subscribed, subscribedToGame);

        // A hack to get the widths of each node to 100% of the vbox
        for (Node node : _leftPaneMenu.getChildren())
            ((Label) node).setPrefWidth(Integer.MAX_VALUE);

        subscribedToGame.setOnMousePressed(e -> {
            if (_selectedGame != null)
                _launcher.subscribeToGame(_selectedGame);
        });
    }

    private void createRightPane() {
        _players = new VBox(1);

        _rightPaneContentBox = new VBox(1);
        _rightPaneContentBox.getStyleClass().addAll("lobby_pane_content", "no_padding");

        Button challengePlayerButton = new Button("Challenge");
        challengePlayerButton.getStyleClass().add("option");
        challengePlayerButton.setPrefWidth(Integer.MAX_VALUE);

        _onlinePlayersLabel = new Label("ONLINE PLAYERS");
        _onlinePlayersLabel.setPrefWidth(width / 4);
        _onlinePlayersLabel.getStyleClass().add("top_label");

        //onlinePlayers.setSpacing(20);
        _usergroup = new ToggleGroup();
        getOnlineUsers();
        addUserSelectionHandlers(challengePlayerButton);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setId("online_users");
        scrollPane.setContent(_players);

        scrollPane.setMinHeight(NUMBER_OF_PLAYERS_WITHOUT_SCROLL * 25);
        scrollPane.setMaxHeight(NUMBER_OF_PLAYERS_WITHOUT_SCROLL * 25);
        scrollPane.setFitToWidth(true);

        // Add everything to the pane
        _rightPaneContentBox.getChildren().addAll(scrollPane, challengePlayerButton);

        _rightPane.setTop(_onlinePlayersLabel);
        _rightPane.setCenter(_rightPaneContentBox);
    }

    private void addGames() {
        for (String game : _gameList) {
            Label label = new Label(game);
            label.getStyleClass().add("option");

            label.setOnMousePressed(e -> {
                _selectedGame = game;

                for (Node node : _leftPaneMenu.getChildren()) {
                    if (node == _subscribed)
                        break;

                    node.getStyleClass().remove("option_selected");
                }

                label.getStyleClass().add("option_selected");

                synchronized (_players) {
                    for (Node player : _players.getChildren()) {
                        if (!(player instanceof RadioButton))
                            continue;

                        if (!((RadioButton) player).getText().equals(_username + " (me)"))
                            player.setDisable(false);
                    }
                }
            });

            label.setPrefWidth(Integer.MAX_VALUE);
            _leftPaneMenu.getChildren().add(label);
        }
    }

    public Node getNode() {
        return _lobbyPane;
    }

    private void getOnlineUsers() {
        Platform.runLater(() -> {
            RadioButton user;
            ListIterator iterator = (ListIterator) _playerList.iterator();

            while (iterator.hasNext()) {
                Player nextPlayer = (Player) iterator.next();
                String usernameString = nextPlayer.getUsername();

                boolean alreadyInList = false;

                synchronized (_players) {
                    for (Node node : _players.getChildren())
                        if (node instanceof RadioButton && (
                                ((RadioButton) node).getText().equals(usernameString) ||
                                ((RadioButton) node).getText().equals(usernameString + " (me)")))
                            alreadyInList = true;
                }

                if (alreadyInList)
                    continue;

                user = new RadioButton(usernameString + (usernameString.equals(_username) ? " (me)" : ""));

                if (_selectedGame == null || usernameString.equals(_username))
                    user.setDisable(true);

                user.setBackground(new Background(new BackgroundFill(nextPlayer.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));

                user.setPrefWidth(Integer.MAX_VALUE);
                user.setToggleGroup(_usergroup);
                user.getStyleClass().add("online_user");

                synchronized (_players) {
                    _players.getChildren().add(user);
                }
            }

            int numberOfPlayers = 0;
            synchronized (_players) {
                Iterator playerIterator = _players.getChildren().iterator();

                while (playerIterator.hasNext()) {
                    Node node = (Node) playerIterator.next();

                    boolean isStillOnline = false;

                    synchronized (_playerList) {
                        for (Player player : _playerList) {
                            if (node instanceof RadioButton && (
                                    ((RadioButton) node).getText().equals(player.getUsername()) ||
                                    ((RadioButton) node).getText().equals(player.getUsername() + " (me)")))
                                isStillOnline = true;
                        }
                    }

                    if (!isStillOnline) {
                        playerIterator.remove();
                        continue;
                    }

                    if (!(node instanceof Label))
                        numberOfPlayers++;
                    else
                        playerIterator.remove();
                }
            }

            _onlinePlayersLabel.setText("ONLINE PLAYERS (" + numberOfPlayers + ")");

            if (numberOfPlayers < NUMBER_OF_PLAYERS_WITHOUT_SCROLL) {
                for (int i = 0; i < NUMBER_OF_PLAYERS_WITHOUT_SCROLL - numberOfPlayers; i++) {
                    Label label = new Label("Searching for player...");
                    label.getStyleClass().add("empty_online_player");
                    label.setPrefWidth(Integer.MAX_VALUE);

                    synchronized (_players) {
                        _players.getChildren().add(label);
                    }
                }
            }
        });
    }

    private void addUserSelectionHandlers(Button button) {
        _rightPane.getChildren().clear();

        button.setOnAction(e -> {
            if (_selectedGame == null)
                return;

            RadioButton chk = (RadioButton) _usergroup.getSelectedToggle();
            String playerToChallenge = chk.getText();

            if (playerToChallenge.equals(_username + " (me)")) {
                Alert challengeMe = new Alert(Alert.AlertType.INFORMATION);

                challengeMe.setTitle("You cannot challenge yourself");
                challengeMe.setHeaderText(null);
                challengeMe.setContentText("You can not challenge yourself! Please challenge another user");

                challengeMe.showAndWait();
            }
            else {
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
        });
    }

    public void setPlayerList(LinkedList<Player> playerList) {
        synchronized (_playerList) {
            _playerList = playerList;
        }

        getOnlineUsers();
    }

    public void setGameList(LinkedList<String> gameList) {
        _gameList = gameList;
        Platform.runLater(() -> populateLeftPane());
    }

    public void setGameMode(String gameMode) {
        _gameMode = gameMode;
    }

    public void setUsername(String username) {
        _username = username;
    }
}
