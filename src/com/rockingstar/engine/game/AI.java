package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.Vector2D;
import com.rockingstar.modules.Reversi.models.ReversiModel;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {

    private ReversiModel _reversiModel;

    public AI(String username) {
        super(username);
        isAI = true;
    }

    public AI(String username, Color color) {
        super(username, color);
        isAI = true;
    }

    public AI(String username, Color color, char character) {
        super(username, color, character);
        isAI = true;
    }

    public void setModel(ReversiModel reversiModel) {
        _reversiModel = reversiModel;
    }

    public Vector2D getMove() {
        ArrayList<Integer> possibleMoves = _reversiModel.getPossibleMoves(this);
        Random random = new Random();
        Integer integer = possibleMoves.get(random.nextInt(possibleMoves.size()));

        return new Vector2D(integer % 8, integer / 8);
    }
}
