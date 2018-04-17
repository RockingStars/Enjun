package com.rockingstar.engine.gui.views;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * @author Rocking Stars
 * @since  beta 1.0
 */
public class MainWindow {

    private Stage _mainStage;
    private BorderPane _layout;
    private Scene _scene;

    /**
     * Creates a BorderPane in a Scene
     * @param mainStage main windows for the GUI
     */
    public MainWindow(Stage mainStage) {
        _mainStage = mainStage;
        _layout = new BorderPane();
        _scene = new Scene(_layout, 1200, 800);
        setup();
    }

    /**
     * Sets screen maximized and adds all the css files
     */
    private void setup() {
        _mainStage.setMaximized(true);
        _mainStage.setResizable(false);
        _scene.getStylesheets().add("styles/login.css");
        _scene.getStylesheets().add("styles/gameSelect.css");
        _scene.getStylesheets().add("styles/TTT.css");
        _scene.getStylesheets().add("styles/reversi.css");

        _mainStage.setScene(_scene);
    }

    /**
     * Centers the Node
     * @param node node to be centered
     */
    public void setCenter(Node node) {
        _layout.setCenter(node);
    }

    /**
     * Sets a title for mainstage
     * @param title String as title for the mainstage
     */
    public void setTitle(String title) {
        _mainStage.setTitle(title);
    }

    /**
     * Add more stylesheets to the GUI
     * @param stylesheet name of the css file to be added
     */
    public void addStylesheet(String stylesheet) {
        _scene.getStylesheets().add("styles/" + stylesheet + ".css");
    }
}
