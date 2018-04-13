package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;

import java.util.ArrayList;

public interface AI {

    void setModel(ReversiModel reversiModel);
    VectorXY getMove(Player player, ArrayList<Integer> possibleMoves);
}
