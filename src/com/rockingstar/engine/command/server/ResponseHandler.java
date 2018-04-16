package com.rockingstar.engine.command.server;

import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.State;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;

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

    private void handleSVR(String response){
        String responseType = response.substring(4).split(" ")[0];

        Launcher launcher = Launcher.getInstance();

        switch(responseType) {
            case "HELP":
                System.out.println("help");
                break;
            case "PLAYERLIST":
                launcher.updatePlayerList(response.substring(14));
                break;
            case "GAMELIST":
                launcher.updateGameList(response.substring(12));
                break;
            case "GAME":
                switch(response.substring(4).split(" ")[1]) {
                    case "MATCH":
                        synchronized (Launcher.LOCK) {
                            Launcher.getInstance().startMatch(response.substring(15));
                        }
                        break;
                    case "YOURTURN":
                        synchronized (Launcher.LOCK) {
                            Util.displayStatus("Entering yourturn thingie");
                            AbstractGame game = launcher.getGame();
                            //game.showPossibleMoves();
                            game.doYourTurn();
                            Util.displayStatus("Yourturn done");
                        }
                        break;
                    case "MOVE":
                        synchronized (Launcher.LOCK) {
                            try {
                                Util.displayStatus("MOVE RESPONSE FROM SERVER: " + response.substring(14));
                                // Remove all characters other than alphanumeric ones and spaces / dashes
                                response = response.replaceAll("[^a-zA-Z0-9 ]", "").split(" ")[6];

                                Thread.sleep(200);
                                launcher.getGame().doPlayerMove(Integer.parseInt(response));
                            } catch (NumberFormatException e) {
                                Util.displayStatus("Received invalid position from server");
                                return;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "CHALLENGE":
                        switch(response.substring(4).replaceAll("[^a-zA-Z0-9 ]", "").split(" ")[2]){
                            case "CANCELLED":
                                System.out.println("cancelled");
                                return;
                            case "CHALLENGER":
                                Launcher.getInstance().challengeReceived(response.substring(19));
                                break;
                        }
                        //Launcher.getInstance().challengeReceived(response.substring(19));
                        break;
                    case "WIN":
                    case "LOSS":
                    case "DRAW":
                        launcher.getGame().setGameState(State.GAME_FINISHED);
                        launcher.getGame().gameEnded(response.substring(4).split(" ")[1]);
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