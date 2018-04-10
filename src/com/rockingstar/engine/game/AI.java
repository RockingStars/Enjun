package com.rockingstar.engine.game;

import javafx.scene.paint.Color;

public class AI extends Player {

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
}
