package com.rockingstar.engine.gamestates;

import com.rockingstar.modules.TicTacToe.GameFinished;
import com.rockingstar.modules.TicTacToe.GameStarted;
import com.rockingstar.modules.TicTacToe.PreGame;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public abstract class Game {

    protected GameState preGame;
    protected GameState gameStarted;
    protected GameState gameFinished;

    protected GameState currentState;

    public Game() {
        preGame = new PreGame(this);
        gameStarted = new GameStarted(this);
        gameFinished = new GameFinished(this);

        currentState = preGame;

    }

    public void setGameState(GameState newGameState){
        currentState = newGameState;
    }

    public void startGame() {
        currentState.startGame();
    }

    public void doPlayerMove(){
        currentState.doPlayerMove();
    }

    public void close(){
        currentState.close();
    }

    public GameState getGameStarted(){
        return gameStarted;
    }

    public GameState getGameFinished(){
        return gameFinished;
    }

    public GameState getCurrentState(){
        return currentState;
    }
}
