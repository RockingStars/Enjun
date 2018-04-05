package com.rockingstar.engine.game;

import javafx.scene.Node;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public interface GameInterface {

    Node getView();
    void setGameState(State newGameState);

    void startGame();
    void doPlayerMove(int x, int y) throws IllegalStateException;
    Player getPlayerToMove() throws IllegalStateException;

    String getTurnMessage(Player player);
    State getGameState();
    int getPlayerScore(Player player) throws IllegalStateException;

    int getPlayerResult();
    int getMoveDetails();
    String getMatchResultComment();
}
