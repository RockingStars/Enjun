package GameStates;

import java.util.Random;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public class GameStarted implements GameState {
    int playerMove;
    private Game game;
    private int playerToMove = 1;
    private int turns = 0;

    public GameStarted(Game game) {
        this.game = game;
    }

    public void doPlayerMove() {
        System.out.println(getMatchStatus());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(game.getCurrentState() != game.getGameFinished()){
            Random random = new Random();
            playerMove = random.nextInt(4) +5;
            System.out.println(getPlayerToMove() + " moved: " + playerMove);
            switchTurns();
        } else {
            if(game.getCurrentState() != game.getGameFinished()) {
                game.setGameState(game.getGameFinished());
            }
        }
    }

    public void switchTurns(){
        turns++;
        if(turns > 5){
            game.setGameState(game.getGameFinished());
        }
        else if(playerToMove == 1){
            setPlayerToMove(2);
        }
        else if(playerToMove == 2){
            setPlayerToMove(1);
        }
        doPlayerMove();
    }

    public int getPlayerToMove(){
        return playerToMove;
    }

    public void setPlayerToMove(int newPlayer){
        playerToMove = newPlayer;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void getView() {

    }

    @Override
    public String getMatchStatus() {
        return "Game State: " + game.getCurrentState().toString();
    }

    @Override
    public void close() {
    }

    @Override
    public String toString(){
        return "Game is Active";
    }

}
