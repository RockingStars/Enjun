package com.rockingstar.engine.gui.controllers;

import com.rockingstar.engine.gui.views.MainWindow;
import com.rockingstar.engine.io.models.Util;
import javafx.scene.Node;
import javafx.stage.Stage;

public class GUIController {

    private Stage _mainStage;
    private MainWindow _view;

    /**
     * Creates new main windows
     * @param mainStage Stage of the GUI
     */
    public GUIController(Stage mainStage) {
        _mainStage = mainStage;
        _view = new MainWindow(mainStage);
        _view.setTitle("Enjun game engine");
    }

    /**
     * centers the included node
     * @param node node to be centered
     */
    public void setCenter(Node node) {
        _view.setCenter(node);
    }

    /**
     * Makes sure the mainstage is shown
     */
    public void display() {
        _mainStage.show();

        Util.displayStatus("Loading GUI");
    }

    /**
     * Adds the stylesheets for the GUI
     * @param stylesheet css files for the layout of the GUI
     */
    public void addStylesheet(String stylesheet) {
        _view.addStylesheet(stylesheet);
    }
}
