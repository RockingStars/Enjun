package com.rockingstar.engine.lobby.controllers;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.AcceptChallengeCommand;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.Player;
import com.rockingstar.engine.gui.controllers.GUIController;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.models.LobbyModel;
import com.rockingstar.engine.lobby.views.LobbyView;
import com.rockingstar.engine.lobby.views.LoginView;
import com.rockingstar.modules.TicTacToe.controllers.TTTController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

public class Launcher {

    private GUIController _guiController;

    private LobbyModel _model;
    private LobbyView _lobbyView;
    private LoginView _loginView;
    private ServerConnection _serverConnection;

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

    public void loadModule(AbstractGame game) {
        _currentGame = game;
        _guiController.setCenter(game.getView());
    }

    public void handleLogin(String username) {
        _localPlayer = new Player(username, new Color(0.5, 0.5, 0.5, 0));
        boolean result = _localPlayer.login();

        if (result) {
            _lobbyView = new LobbyView(_model.getPlayerList(), _model.getGameList());

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
            challengeInvitationAlert.setTitle("Unable to login");
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
        String gametype = parts[3];
        String opponentName = parts[5];

        Player opponent = new Player(opponentName);
        Platform.runLater(() -> {
            AbstractGame gameModule = new TTTController(_localPlayer, opponent);
            if(startingPlayer.equals(opponentName)){
                gameModule.setCurrentPlayer(1);
                gameModule.setYourTurn(false);
            } else{
                gameModule.setCurrentPlayer(0);
                gameModule.setYourTurn(true);
            }
            loadModule(gameModule);
        });
    }

    public AbstractGame getGame() {
        return _currentGame;
    }
}
