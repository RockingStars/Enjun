package com.rockingstar.engine.lobby.controllers;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.AcceptChallengeCommand;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.GetPlayerListCommand;
import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.Lech;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.gui.controllers.GUIController;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.models.LobbyModel;
import com.rockingstar.engine.lobby.views.LobbyView;
import com.rockingstar.engine.lobby.views.LoginView;
import com.rockingstar.modules.Reversi.controllers.ReversiController;
import com.rockingstar.modules.TicTacToe.controllers.TTTController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Launcher {

    private GUIController _guiController;

    private LobbyModel _model;
    private LobbyView _lobbyView;
    private ServerConnection _serverConnection;

    private LoginView _loginView;

    private AbstractGame _currentGame;

    private static Launcher _instance;

    private Player _localPlayer;

    private Launcher(GUIController guiController, ServerConnection serverConnection) {
        _guiController = guiController;
        _serverConnection = serverConnection;

        _model = new LobbyModel(this);
        _loginView = new LoginView();

        _model.addLoginActionHandlers(_loginView, this);
    }

    public static Launcher getInstance() {
        if (_instance == null)
            return null;

        return _instance;
    }

    public static Launcher getInstance(GUIController guiController, ServerConnection serverConnection) {
        if (_instance == null)
            _instance = new Launcher(guiController, serverConnection);

        return _instance;
    }

    public void setCentralNode() {
        _guiController.setCenter(_loginView.getNode());
    }

    public void returnToLobby() {
        _guiController.setCenter(_lobbyView.getNode());
        _currentGame = null;
    }

    private void loadModule(AbstractGame game) {
        _currentGame = game;
        _guiController.setCenter(game.getView());
    }


    public void handleLogin(String username, String gameMode, boolean isAI) {
        // @todo Check for difficulty
        if (isAI)
            _localPlayer = new Lech(username, new Color(0.5, 0.5, 0.5, 0));
        else
            _localPlayer = new Player(username, new Color(0.5, 0.5, 0.5, 0));

        if (_localPlayer.login()) {
            _lobbyView = new LobbyView(getPlayerList(), _model.getGameList());

            _lobbyView.setGameMode(gameMode);
            _lobbyView.setUsername(_localPlayer.getUsername());
            _model.setLocalPlayer(_localPlayer);

            _guiController.setCenter(_lobbyView.getNode());
            _model.addGameSelectionActionHandlers(_lobbyView);
        }
    }

    public void challengeReceived(String response) {
        String[] parts = response.replaceAll("[^a-zA-Z0-9 ]","").split(" ");

        String challenger = parts[1];
        int challengeNumber;
        String gameType = parts[5];

        try {
            challengeNumber = Integer.parseInt(parts[3]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid challenge");
            return;
        }

        Platform.runLater(() -> {
            Alert challengeInvitationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            challengeInvitationAlert.setTitle("Challenge received");
            challengeInvitationAlert.setHeaderText(null);
            challengeInvitationAlert.setContentText("Player " + challenger + " has invited you to a game of " + gameType + ". Do you accept?");

            challengeInvitationAlert.showAndWait();

            if (challengeInvitationAlert.getResult() == ButtonType.OK) {
                CommandExecutor.execute(new AcceptChallengeCommand(_serverConnection, challengeNumber));
                Util.displayStatus("Accepting challenge from " + challenger);
            }
        });
    }

    public void startMatch(String response) {
        String[] parts = response.replaceAll("[^a-zA-Z0-9 ]","").split(" ");

        String startingPlayer = parts[1];
        String gameType = parts[3];
        String opponentName = parts[5];

        Player opponent = new Player(opponentName);

        Platform.runLater(() -> {
            AbstractGame gameModule;

            switch (gameType) {
                case "Tic-tac-toe":
                case "Tictactoe":
                    gameModule = new TTTController(_localPlayer, opponent);
                    break;
                case "Reversi":
                    gameModule = new ReversiController(_localPlayer, opponent);
                    ((ReversiController) gameModule).setStartingPlayer(startingPlayer.equals(opponentName) ? opponent : _localPlayer);
                    break;
                default:
                    Util.displayStatus("Failed to load game module " + gameType);
                    return;
            }

            Util.displayStatus("Loading game module " + gameType, true);

            loadModule(gameModule);
            gameModule.startGame();
        });
    }

    public LinkedList<Player> getPlayerList() {
        LinkedList<Player> players = new LinkedList<>();

        ServerConnection serverConnection = ServerConnection.getInstance();
        CommandExecutor.execute(new GetPlayerListCommand(serverConnection));

        if (serverConnection.isValidCommand())
            for (String player : Util.parseFakeCollection(serverConnection.getResponse()))
                players.add(new Player(player));
        else
            Util.displayStatus("Loading player list", false);

        return players;
    }

    public AbstractGame getGame() {
        return _currentGame;
    }
}