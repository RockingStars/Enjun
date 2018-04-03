package GameStates;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public class GameFinished implements GameState {

    Game game;

    public GameFinished(Game game) {
        this.game = game;
    }

    public void close(){
        System.out.println(getMatchStatus());
        System.out.println("Closing Game");
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
    public void doPlayerMove() {

    }

    @Override
    public String toString(){
        return "Game Finished";
    }
}
