package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Bas extends Player implements AI {

    private ReversiModel _reversiModel;

    public Bas(String username) {
        super(username);
        isAI = true;
    }

    public Bas(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public Bas(String username, Color color, char character) {
        super(username, color, character);
        isAI = true;
    }

    @Override
    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    @Override
    public VectorXY getMove(Player player) {
        Integer move;
        System.out.println("poss moves in get move" +_reversiModel.getPossibleMoves(player));
        ArrayList<Integer> possibleMoves = _reversiModel.getPossibleMoves(player);
        Random random = new Random();

        System.out.printf("Possible moves for Lech: %d\n", possibleMoves.size());

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
