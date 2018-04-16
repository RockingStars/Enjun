package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class EasyAI extends Player implements AI {

    private ReversiModel _reversiModel;

    /**
     * Method to instantiate the EasyAI
     * @param username
     * @param color
     */
    public EasyAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    /**
     * Method to set the model of the class
     * @param reversiModel
     */
    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    /**
     * Method to get the possible moves and a check for corners.
     * @param player
     * @param possibleMoves
     * @return Returns the vector to make a move
     */
    @Override
    public VectorXY getMove(Player player, ArrayList<Integer> possibleMoves) {
        Integer move;
        System.out.println("poss moves in get move" +_reversiModel.getPossibleMoves(player));
        Random random = new Random();

        System.out.printf("Possible moves for EasyAI: %d\n", possibleMoves.size());

        if(possibleMoves.contains(0)) {
            move = 0;
        } else if (possibleMoves.contains(7)){
            move = 7;
        } else if (possibleMoves.contains(56)){
            move = 56;
        } else if (possibleMoves.contains(63)){
            move = 63;
        } else {
            move = possibleMoves.get(random.nextInt(possibleMoves.size()));
        }
        return new VectorXY(move % 8, move / 8);
    }
}
