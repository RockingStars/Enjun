package com.rockingstar.engine.command.server;

import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.State;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
import javafx.application.Platform;

public class ResponseHandler {

    private String _message;
    private boolean _isValidCommand;

    public ResponseHandler() {

    }

    public void handle(String response) {
        String responseType = response.split(" ")[0];
        _isValidCommand = false;

        // test of server niet ofline is (kijken of je response krijgt)
        // System.out.println(responseType);
        switch(responseType){
            case "OK":
                _isValidCommand = true;
                break;
            case "ERR":
                _message = response.substring(4);
                break;
            case "SVR":
                _isValidCommand = true;
                handleSVR(response);
        }
    }

    public void handleSVR(String response){
        String responseType = response.substring(4).split(" ")[0];

        switch(responseType){
            case "HELP":
                System.out.println("help");
                break;
            case "PLAYERLIST":
                _message = response.substring(14);
                break;
            case "GAMELIST":
                _message = response.substring(12);
                break;
            case "GAME":
                switch(response.substring(4).split(" ")[1]) {
                    case "MATCH":
                        Launcher.getInstance().startMatch(response.substring(15));
                        break;
                    case "YOURTURN":
                        Platform.runLater(() -> {
                            AbstractGame game = Launcher.getInstance().getGame();
                            game.setYourTurn(true);
                            game.setCurrentPlayer(0);
                        });
                        break;
                    case "MOVE":
                        try {
                            System.out.println("MOVE RESPONSE FROM SERVER: " + response.substring(14));
                            response = response.replaceAll("[^a-zA-Z0-9 ]","").split(" ")[6];
                            Launcher.getInstance().getGame().doPlayerMove(Integer.parseInt(response));
                            if(Launcher.getInstance().getGame().getGameState() == State.GAME_FINISHED){
                                Launcher.getInstance().getGame().gameEnded();
                            }
                        }
                        catch (NumberFormatException e) {
                            Util.displayStatus("Received invalid position from server");
                            return;
                        }
                        break;
                    case "CHALLENGE":
                        Launcher.getInstance().challengeReceived(response.substring(19));
                        break;
                    case "WIN":
                    case "LOSS":
                    case "DRAW":
                        Launcher.getInstance().getGame().setGameState(State.GAME_FINISHED);
                        Launcher.getInstance().getGame().gameEnded();
                        break;
                }
        }
    }

    public String getMessage() {
        return _message;
    }

    public boolean isValidCommand() {
        return _isValidCommand;
    }
}
