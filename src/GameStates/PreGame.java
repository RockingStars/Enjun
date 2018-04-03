package GameStates;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public class PreGame implements GameState {

    Game game;

    public PreGame(Game game) {
        this.game = game;
    }

    public void startGame() {
        System.out.println(getMatchStatus());
        System.out.println("Starting Game");
        game.setGameState(game.getGameStarted());
    }

    @Override
    public void getView() {

    }

    public String getMatchStatus() {
        return "Game State: " + game.getCurrentState().toString();
    }

    @Override
    public void doPlayerMove() {

    }

    @Override
    public void close() {

    }


    @Override
    public String toString(){
        return "PreGame";
    }


}
