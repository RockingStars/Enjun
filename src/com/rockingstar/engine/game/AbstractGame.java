package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.Player;
import javafx.scene.Node;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public abstract class AbstractGame implements GameInterface {

    protected State currentState;

    /**
     * State code for the current move.
     * -1   Invalid move
     * 0    No move has been made yet
     * 1    Valid move
     */
    protected int moveDetails;

    /**
     * The player's result
     * 0    Draw
     * 1    Won
     */
    protected int playerResult;

    public AbstractGame() {
        currentState = State.PREGAME;
    }

    @Override
    public abstract Node getView();

    @Override
    public void setGameState(State newGameState) {
        currentState = newGameState;
    }

    @Override
    public void startGame() {
        currentState = State.GAME_STARTED;
    }

    @Override
    public void doPlayerMove() throws IllegalStateException {
        if (currentState != State.GAME_STARTED)
            throw new IllegalStateException();
    }

    @Override
    public void getPlayerToMove() throws IllegalStateException {
        if (currentState != State.GAME_STARTED)
            throw new IllegalStateException();
    }

    @Override
    public String getTurnMessage() {
        return null;
    }

    @Override
    public final State getGameState() {
        return currentState;
    }

    @Override
    public int getPlayerScore(Player player) {
        return player.getScore();
    }

    @Override
    public int getPlayerResult() throws IllegalStateException {
        if (currentState != State.GAME_FINISHED)
            throw new IllegalStateException();

        return playerResult;
    }

    @Override
    public final int getMoveDetails() {
        return moveDetails;
    }

    @Override
    public String getMatchResultComment() {
        return null;
    }
}
