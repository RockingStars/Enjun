package com.rockingstar.engine.game;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.LoginCommand;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.util.Random;

public class Player {

    protected String username;
    protected int score;
    protected Color color;

    protected boolean isAI = false;

    protected char _character;

    private static final Color[] COLORS = {
        Color.valueOf("2766adbb"),
        Color.valueOf("adad27bb"),
        Color.valueOf("ad2727bb"),
        Color.valueOf("27ad44bb")
    };

    public Player(String username) {
        this.username = username;
        score = 0;

        color = COLORS[new Random().nextInt(COLORS.length)];
    }

    public Player(String username, Color color) {
        this.username = username;
        score = 0;
        this.color = color;
    }

    public Player(String username, Color color, char character) {
        this.username = username;
        score = 0;
        this.color = color;
        _character = character;
    }

    public boolean login() {
        ServerConnection serverConnection = ServerConnection.getInstance();

        CommandExecutor.execute(new LoginCommand(serverConnection, username));

        if (!serverConnection.isValidCommand()) {
            Alert uNameAlert = new Alert(Alert.AlertType.INFORMATION);
            uNameAlert.setTitle("Unable to login");
            uNameAlert.setHeaderText("Unable to login");
            uNameAlert.setContentText(serverConnection.getResponse());
            uNameAlert.showAndWait();

            return false;
        }

        return true;
    }

    public String getUsername() {
        return username;
    }

    public Color getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public char getCharacter() {
        return _character;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCharacter(char character) {
        _character = character;
    }

    public void setIsAI(boolean isAI) {
        this.isAI = isAI;
    }
}
