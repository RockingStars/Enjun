package com.rockingstar.engine.gui.views;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow {

    private Stage _mainStage;
    private BorderPane _layout;
    private Scene _scene;

    public MainWindow(Stage mainStage) {
        _mainStage = mainStage;
        _layout = new BorderPane();
        _scene = new Scene(_layout, 1200, 800);
        setup();
    }

    private void setup() {
        _mainStage.setMaximized(true);
        //_mainStage.setResizable(false);
        _scene.getStylesheets().add("styles/login.css");
        _scene.getStylesheets().add("styles/gameSelect.css");
        _scene.getStylesheets().add("styles/TTT.css");
        _scene.getStylesheets().add("styles/reversi.css");

        _mainStage.setScene(_scene);
    }

    public void setCenter(Node node) {
        _layout.setCenter(node);
    }

    public void setTitle(String title) {
        _mainStage.setTitle(title);
    }

    public void addStylesheet(String stylesheet) {
        _scene.getStylesheets().add("styles/" + stylesheet + ".css");
    }
}
