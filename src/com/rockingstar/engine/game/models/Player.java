package com.rockingstar.engine.game.models;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.LoginCommand;
import com.rockingstar.engine.io.models.CommandExecutor;
import javafx.scene.paint.Color;

public class Player {

    private String _username;
    private int _score;
    private Color _color;

    private char _character;

    public Player(String username, Color color) {
        _username = username;
        _score = 0;
        _color = color;
    }

    public Player(String username, Color color, char character) {
        _username = username;
        _score = 0;
        _color = color;
        _character = character;
    }

    public void login() {
        CommandExecutor.execute(new LoginCommand(ServerConnection.getInstance(), _username));
        ReceivedMessageHandler messageHandler = CommandExecutor.readReply();
        System.out.println(messageHandler);
    }

    public String getUsername() {
        return _username;
    }

    public Color getColor() {
        return _color;
    }

    public int getScore() {
        return _score;
    }

    public char getCharacter() {
        return _character;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public void setScore(int score) {
        _score = score;
    }

    public void setColor(Color color) {
        _color = color;
    }

    public void setCharacter(char character) {
        _character = character;
    }
}
