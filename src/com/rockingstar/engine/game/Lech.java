package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Lech extends Player implements AI {

    private ReversiModel _reversiModel;

    public Lech(String username) {
        super(username);
        isAI = true;
    }

    public Lech(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public Lech(String username, Color color, char character) {
        super(username, color, character);
        isAI = true;
    }

    @Override
    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    @Override
    public VectorXY getMove(Player player) {
        System.out.println("poss moves in get move" +_reversiModel.getPossibleMoves(player));
        ArrayList<Integer> possibleMoves = _reversiModel.getPossibleMoves(player);
        Random random = new Random();

        System.out.printf("Possible moves for Lech: %d\n", possibleMoves.size());

        Integer integer = possibleMoves.get(random.nextInt(possibleMoves.size() - 1));

        return new VectorXY(integer % 8, integer / 8);
    }
}
