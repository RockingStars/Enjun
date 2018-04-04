package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.Player;
import javafx.scene.Node;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public interface GameInterface {

    Node getView();
    void setGameState(State newGameState);

    void startGame();
    void doPlayerMove();
    void getPlayerToMove();

    String getTurnMessage();
    State getGameState();
    int getPlayerScore(Player player);

    int getPlayerResult();
    int getMoveDetails();
    String getMatchResultComment();
}
