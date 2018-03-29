package com.rockingstar.engine.controllers;

import com.rockingstar.engine.gui.controllers.GUI;
import com.rockingstar.engine.io.models.Util;
import javafx.application.Application;
import javafx.stage.Stage;

public class Engine extends Application {

    private GUI _gui;

    public void boot() {
        Util.displayStatus("Entering boot sequence");
        _gui.display();

        Util.displayStatus("Boot sequence completed. Welcome to Enjun!");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        _gui = new GUI(primaryStage);
        boot();
    }
}