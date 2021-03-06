package com.rockingstar.engine.command.server;

import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.game.State;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
/**
 * @author Rocking Stars
 * @since  beta 1.0
 */
public class ResponseHandler {

    private String _message;
    private boolean _isValidCommand;

    /**
     * Handles the responses from the server
     * @param response the received response from the server
     */
    public void handle(String response) {
        String responseType = response.split(" ")[0];
        _isValidCommand = false;

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

    /**
     * Handles every server messages starting with SVR
     * @param response contains the server message
     */
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
                            launcher.startMatch(response.substring(15));
                        }
                        break;
                    case "YOURTURN":
                        synchronized (Launcher.LOCK) {
                            AbstractGame game = launcher.getGame();
                            game.doYourTurn();
                        }
                        break;
                    case "MOVE":
                        synchronized (Launcher.LOCK) {
                            try {
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
                                Util.displayStatus("Challenge was cancelled");
                                return;
                            case "CHALLENGER":
                                launcher.challengeReceived(response.substring(19));
                                break;
                        }

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

    /**
     * Returns a substring, which is set in handle()
     * @return substring from response
     */
    public String getMessage() {
        return _message;
    }

    /**
     * Checks if a command is a valid command
     * @return Boolean, true if valid command, false if not
     */
    public boolean isValidCommand() {
        return _isValidCommand;
    }
}