package com.rockingstar.engine.lobby.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginView {

    private GridPane _gridPane;
    private Button _continueButton;
    private TextField _usernameTextField;

    public LoginView() {
        _gridPane = new GridPane();
        setup();
    }

    private void setup() {
        _gridPane.setPadding(new Insets(15));
        _gridPane.setHgap(5);
        _gridPane.setVgap(5);
        _gridPane.setAlignment(Pos.CENTER);

        _gridPane.add(new Label("Please enter a username"),0,0);
        _usernameTextField = new TextField();
        _gridPane.add(_usernameTextField,0,1);
        _continueButton = new Button("Continue");
        _gridPane.add(_continueButton,0,25);

        GridPane.setHalignment(_continueButton, HPos.LEFT);
    }

    public Node getNode() {
        return _gridPane;
    }

    public Button getContinueButton() {
        return _continueButton;
    }

    public String getInsertedUsername() {
        return _usernameTextField.getText();
    }
}
