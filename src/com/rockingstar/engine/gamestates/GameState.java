package com.rockingstar.engine.gamestates;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public interface GameState {

    void startGame();
    void getView();
    String getMatchStatus();
    void doPlayerMove();
    void close();
    String toString();
}
