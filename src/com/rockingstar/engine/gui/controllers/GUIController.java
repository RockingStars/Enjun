package com.rockingstar.engine.gui.controllers;

import com.rockingstar.engine.gui.views.MainWindow;
import com.rockingstar.engine.io.models.Util;
import javafx.scene.Node;
import javafx.stage.Stage;

public class GUIController {

    private Stage _mainStage;
    private MainWindow _view;

    public GUIController(Stage mainStage) {
        _mainStage = mainStage;
        _view = new MainWindow(mainStage);
        _view.setTitle("Enjun game engine");
    }

    public void setCenter(Node node) {
        _view.setCenter(node);
    }

    public void display() {
        _mainStage.show();

        Util.displayStatus("Loading GUI");
    }
}
