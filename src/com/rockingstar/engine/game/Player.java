/*
 * Enjun
 *
 * @version     1.0 Beta 1
 * @author      Rocking Stars
 * @copyright   2018, Enjun
 *
 * Copyright 2018 RockingStars

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        Color.valueOf("2766ad"),
        Color.valueOf("adad27"),
        Color.valueOf("ad2727"),
        Color.valueOf("27ad44")
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCharacter(char character) {
        _character = character;
    }
}
