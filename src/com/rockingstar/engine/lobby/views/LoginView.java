package com.rockingstar.engine.lobby.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;

public class LoginView {

    private BorderPane _borderPane;
    private Button _continueButton;
    private TextField _usernameTextField;
    private Label _playerMode;
    public String _selectedGameMode;
    public String _selectedDifficulty;

    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    double width = graphicsDevice.getDisplayMode().getWidth();
    double height = graphicsDevice.getDisplayMode().getHeight();

    public LoginView() {
        _borderPane = new BorderPane();
        setup();
    }

    private void setup() {
        _borderPane.setPadding(new Insets(100,15,15,15));
        //Top
        Label welcomeMessage = new Label("Login");
        welcomeMessage.setAlignment(Pos.CENTER);
        welcomeMessage.setMinWidth(width);
        _borderPane.setTop(welcomeMessage);

        welcomeMessage.setId("welcome");

        //Center

        // TopCenter
        VBox centerPane = new VBox();
        _playerMode = new Label("How do you want to play?");
        _playerMode.setId("login_label");

        //MidCenter
        HBox _radioButtons = new HBox(30);
        ToggleGroup playMode = new ToggleGroup();

        RadioButton player = new RadioButton("Player");
        player.setUserData("Player");
        RadioButton AI = new RadioButton("AI");
        AI.setUserData("AI");

        player.setId("player_type");
        AI.setId("player_type");
        _radioButtons.setAlignment(Pos.CENTER);


        player.setToggleGroup(playMode);
        AI.setToggleGroup(playMode);
        _radioButtons.getChildren().addAll(player,AI);


        //BottomCenter
        VBox userName = new VBox(10);
        Label enterUname = new Label("Please enter your username:");
        _usernameTextField = new TextField();
        _usernameTextField.setVisible(false);
        _usernameTextField.setPromptText("Username");
        enterUname.setVisible(false);
        _usernameTextField.setMaxWidth(300);
        _usernameTextField.setId("username_input");
        enterUname.setId("login_label");
        userName.getChildren().addAll(enterUname, _usernameTextField);
        userName.setAlignment(Pos.CENTER);


        //AI difficulty
        Label difficulty = new Label("Please select the AI difficulty");
        difficulty.setId("login_label");
        difficulty.setVisible(false);
        HBox AIMode = new HBox(30);
        ToggleGroup AIGroup= new ToggleGroup();

        RadioButton lech = new RadioButton("Lech");
        lech.setUserData("Lech");
        RadioButton bas = new RadioButton("Bas");
        bas.setUserData("Bas");

        lech.setId("player_type");
        bas.setId("player_type");
        AIMode.setAlignment(Pos.CENTER);


        lech.setToggleGroup(AIGroup);
        bas.setToggleGroup(AIGroup);
        AIMode.setVisible(false);
        AIMode.getChildren().addAll(lech, bas);



        //Continue
        _continueButton = new Button("Continue");
        _continueButton.setVisible(false);
        _continueButton.setId("button");

        centerPane.getChildren().addAll(_playerMode, _radioButtons, difficulty, AIMode, userName, _continueButton);
        _borderPane.setCenter(centerPane);


        centerPane.setMaxHeight(height/2);
        centerPane.setMaxWidth(width/2);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setId("centerPane");
        centerPane.setSpacing(50);

        playMode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                _usernameTextField.setVisible(true);
                _continueButton.setVisible(true);
                enterUname.setVisible(false);
                if (playMode.getSelectedToggle().getUserData().toString() == "AI"){
                    difficulty.setVisible(true);
                    AIMode.setVisible(true);
                    _continueButton.setVisible(false);
                    _usernameTextField.setVisible(false);
                    enterUname.setVisible(false);
                } else{
                    difficulty.setVisible(false);
                    AIMode.setVisible(false);
                    enterUname.setVisible(true);
                }
                _selectedGameMode = playMode.getSelectedToggle().getUserData().toString();
            }
        });

        AIGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                _selectedDifficulty = AIGroup.getSelectedToggle().getUserData().toString();
                if (AIGroup.getSelectedToggle() != null){
                    enterUname.setVisible(true);
                    _usernameTextField.setVisible(true);
                    _continueButton.setVisible(true);
                } //else {
//
//                }


                System.out.println(_selectedDifficulty);
            }
        });

    }

    public Node getNode() {
        return _borderPane;
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

    public String getGamemode(){
        return _selectedGameMode;
    }

    public String getDifficulty(){
        return _selectedDifficulty;
    }
}
