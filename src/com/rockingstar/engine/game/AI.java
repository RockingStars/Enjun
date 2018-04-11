package com.rockingstar.engine.game;

import com.rockingstar.engine.game.models.VectorXY;
import com.rockingstar.modules.Reversi.models.ReversiModel;

public interface AI {

    void setModel(ReversiModel reversiModel);
    VectorXY getMove();
}
