package com.rockingstar.engine.controllers;

import com.rockingstar.engine.ServerConnection;
import com.rockingstar.engine.command.client.CommandExecutor;
import com.rockingstar.engine.command.client.LogoutCommand;
import com.rockingstar.engine.gui.controllers.GUIController;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class Engine extends Application {

    private GUIController _gui;
    private ServerConnection _serverConnection;
    private Launcher _launcher;

    private void boot() {
        Util.displayStatus("Entering boot sequence");

        _gui.display();
        _serverConnection = ServerConnection.getInstance();
        _serverConnection.start();

        // @todo Move to own class
        setBackgroundMusic();

        Util.displayStatus("Boot sequence completed. Welcome to Enjun!");

        _launcher = Launcher.getInstance(_gui, _serverConnection);
        _launcher.setCentralNode();

        //_serverConnection.close();
    }

    @Override
    public void start(Stage primaryStage) {
        _gui = new GUIController(primaryStage);
        primaryStage.setOnCloseRequest(e -> {
            Util.displayStatus("Preparing to exit engine on request by user");
            CommandExecutor.execute(new LogoutCommand(ServerConnection.getInstance()));
            _serverConnection.close();
            System.exit(0);
        });
        boot();
    }

    private void setBackgroundMusic() {
        new Thread(() -> {
            URL resource = getClass().getClassLoader().getResource("resources/sound/music/LobbyMusic.mp3");

            Util.displayStatus("Loading background music", resource != null);

            if (resource == null)
                return;

            MediaPlayer player = new MediaPlayer(new Media(resource.toString()));
            player.play();
            player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        }).start();
    }
}