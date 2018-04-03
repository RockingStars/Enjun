package GameStates;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public class Game {

    GameState preGame;
    GameState gameStarted;
    GameState gameFinished;

    GameState currentState;

    public Game(){
        preGame = new PreGame(this);
        gameStarted = new GameStarted(this);
        gameFinished = new GameFinished(this);

        currentState = preGame;

    }

    void setGameState(GameState newGameState){
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
