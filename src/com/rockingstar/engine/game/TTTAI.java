package com.rockingstar.engine.game;

import com.rockingstar.engine.game.AI;
import com.rockingstar.engine.game.Player;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.TicTacToe.models.TTTModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;


public class TTTAI extends Player implements AI {
    private TTTModel _TTTModel;

    public TTTAI(String username) {
        super(username);
        isAI = true;
    }

    public TTTAI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public void setModel(TTTModel tttModel) {
        _TTTModel = tttModel;
    }

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
