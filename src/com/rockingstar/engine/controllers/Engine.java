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

package com.rockingstar.engine.controllers;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.LogoutCommand;
import com.rockingstar.engine.gui.controllers.GUIController;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;

/**
 * @author Rocking Stars
 * @since  beta 1.0
 */


public class Engine extends Application {

    private GUIController _gui;
    private Launcher _launcher;

    /**
     * Boots the engine.
     * Starts the gui and sets up the server connection
     */
    private void boot() {
        Util.displayStatus("Entering boot sequence");

        _gui.display();

        Util.displayStatus("Boot sequence completed. Welcome to Enjun!");

        _launcher = Launcher.getInstance(_gui);
        _launcher.setCentralNode();
    }

    /***
     * Sets the GUI to primaryStage, when primaryStage is closed, user is logged out.
     * @param primaryStage The GUI
     */
    @Override
    public void start(Stage primaryStage) {
        _gui = new GUIController(primaryStage);
        primaryStage.setOnCloseRequest(e -> {
            Util.displayStatus("Preparing to exit engine on request by user");

            ServerConnection serverConnection = ServerConnection.getInstance();
            if (serverConnection != null) {
                CommandExecutor.execute(new LogoutCommand(ServerConnection.getInstance()));
                serverConnection.close();
            }

            System.exit(0);
        });
        boot();
    }
}