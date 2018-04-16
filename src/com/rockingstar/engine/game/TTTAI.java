package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.TicTacToe.models.TTTModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains methods that create and update a TicTacToe AI
 */
public class TTTAI extends Player implements AI {
    private TTTModel _TTTModel;

    /**
     * Method to instantiate a TTT AI
     * @param username
     */
    public TTTAI(String username) {
        super(username);
        isAI = true;
    }

    /**
     * Method to instantiate a TTT AI
     * @param username
     * @param color
     */
    public TTTAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    /**
     * Method to set the model
     * @param tttModel
     */
    public void setModel(TTTModel tttModel) {
        _TTTModel = tttModel;
    }

    /**
     * Method to make a random move
     * @param player
     * @param possibleMoves
     * @return Returns the vector to make a move
     */
    @Override
    public VectorXY getMove(Player player, ArrayList<Integer> possibleMoves) {
        Integer move;
        System.out.println("poss moves in get move" + possibleMoves);
        Random random = new Random();

        System.out.printf("Possible moves for TTTAI: %d\n", possibleMoves.size());

        move = possibleMoves.get(random.nextInt(possibleMoves.size()));

        return new VectorXY(move % 3, move / 3);
    }
}
