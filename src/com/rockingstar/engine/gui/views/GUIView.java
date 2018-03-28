package com.rockingstar.engine.gui.views;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUIView {

    private Stage _mainWindow;
    private BorderPane _layout;
    private Scene _scene;

    public GUIView(Stage mainWindow) {
        _mainWindow = mainWindow;
        _layout = new BorderPane();
        _scene = new Scene(_layout);
        setup();
    }

    private void setup() {
        _mainWindow.setMaximized(true);
        _scene.getStylesheets().add("styles/base.css");
        _mainWindow.setScene(_scene);
    }

    public void setCenter(Node node) {
        _layout.setCenter(node);
    }

    public void setTitle(String title) {
        _mainWindow.setTitle(title);
    }
}
