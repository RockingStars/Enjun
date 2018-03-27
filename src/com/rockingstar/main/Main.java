package com.rockingstar.main;

import com.rockingstar.engine.controllers.Engine;
import com.rockingstar.engine.gui.controllers.GUI;
import com.rockingstar.engine.io.models.Util;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main {

    public static final boolean DEBUG_MODE = true;

    public static void main(String[] args) {
        System.out.println(":: Welcome to Enjun");
        Util.displayStatus("Starting pre-launch checks");
        Util.displayStatus("Debug mode is on");
        launch(Engine.class, args);
    }
}
