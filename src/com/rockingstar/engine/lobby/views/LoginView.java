package com.rockingstar.engine.lobby.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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

        Label welcomeMessage = new Label("Welcome to RockingStar Games");
        Label uNameText = new Label("Please enter a username");
        _gridPane.add(welcomeMessage,0,0,2,1);
        _gridPane.add(uNameText, 0,1,2,1);

        welcomeMessage.setId("welcome");
        uNameText.setId("uName");

        _usernameTextField = new TextField();


        _gridPane.add(_usernameTextField,0,3);
        _continueButton = new Button("Continue");
        _gridPane.add(_continueButton,0,20);

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

    public TextField getUsernameTextField() {
        return _usernameTextField;
    }
}
