package com.rockingstar.engine.lobby.views;

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
    private String _selectedGameMode;
    private String _selectedDifficulty;

    private GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private double width = graphicsDevice.getDisplayMode().getWidth();
    private double height = graphicsDevice.getDisplayMode().getHeight();

    private ToggleGroup _playMode;
    private ToggleGroup _AIGroup;
    private Label _enteredUsername;
    private Label _difficulty;
    private HBox _AIMode;

    private Label _hostIP;
    private TextField _hostIPInput;

    public LoginView() {
        _borderPane = new BorderPane();
        setup();
    }

    /**
     * Method to setup the login view
     */
    private void setup() {
        _borderPane.setPadding(new Insets(100,15,15,15));

        createTopPane();
        createCenterPane();
        addActionHandlers();
    }

    /**
     * Method to create the top of the border pane
     */
    private void createTopPane() {
        Label welcomeMessage = new Label("Welcome to Rockingstar Games!");
        welcomeMessage.setAlignment(Pos.CENTER);
        welcomeMessage.setMinWidth(width);
        welcomeMessage.setId("welcome");

        _borderPane.setTop(welcomeMessage);
    }

    /**
     * Method to create the center of the border pane
     */
    private void createCenterPane() {
        VBox centerPane = new VBox();
        centerPane.setMaxHeight(height/1.5);
        centerPane.setMaxWidth(width/2);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setId("centerPane");
        centerPane.setSpacing(30);

        Label playerMode = new Label("Choose your player type");
        playerMode.setId("login_label");

        _hostIP = new Label("Host address");
        _hostIP.setId("host_ip_label");
        _hostIP.setVisible(false);

        _hostIPInput = new TextField("77.162.40.81:7789");
        _hostIPInput.setPromptText("127.0.0.1:7789");
        _hostIPInput.setId("hostname_input");
        _hostIPInput.setMaxWidth(300);
        _hostIPInput.setVisible(false);

        HBox radioButtons = new HBox(30);
        radioButtons.setAlignment(Pos.CENTER);

        _playMode = new ToggleGroup();
        _AIGroup = new ToggleGroup();

        RadioButton player = new RadioButton("Player");
        player.setUserData("Player");
        player.setId("player_type");
        player.setToggleGroup(_playMode);

        RadioButton AI = new RadioButton("AI");
        AI.setUserData("AI");
        AI.setId("player_type");
        AI.setToggleGroup(_playMode);

        VBox userName = new VBox(10);
        _usernameTextField = new TextField();
        _usernameTextField.setVisible(false);
        _usernameTextField.setPromptText("Username");
        _usernameTextField.setMaxWidth(300);
        _usernameTextField.setId("username_input");

        _enteredUsername = new Label("Enter your username:");
        _enteredUsername.setVisible(false);
        _enteredUsername.setId("login_label");

        userName.getChildren().addAll(_enteredUsername, _usernameTextField);
        userName.setAlignment(Pos.CENTER);

        _difficulty = new Label("Select AI difficulty");
        _difficulty.setId("login_label");
        _difficulty.setVisible(false);

        RadioButton easyAI = new RadioButton("EasyAI");
        easyAI.setUserData("EasyAI");
        easyAI.setId("player_type");
        easyAI.setToggleGroup(_AIGroup);

        RadioButton hardAI = new RadioButton("HardAI");
        hardAI.setUserData("HardAI");
        hardAI.setId("player_type");
        hardAI.setToggleGroup(_AIGroup);

        _AIMode = new HBox(30);
        _AIMode.getChildren().addAll(easyAI, hardAI);
        _AIMode.setVisible(false);
        _AIMode.setAlignment(Pos.CENTER);

        radioButtons.getChildren().addAll(player,AI);

        _continueButton = new Button("Continue");
        _continueButton.setVisible(false);
        _continueButton.setId("button");

        centerPane.getChildren().addAll(playerMode, radioButtons, _difficulty,
            _AIMode, userName, _hostIP, _hostIPInput, _continueButton
        );
        _borderPane.setCenter(centerPane);
    }

    /**
     * Method to add action handlers to the buttons of the login view
     */
    private void addActionHandlers() {
        _playMode.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            _usernameTextField.setVisible(true);
            _continueButton.setVisible(true);
            _enteredUsername.setVisible(false);
            _selectedGameMode = _playMode.getSelectedToggle().getUserData().toString();
            if (_selectedGameMode.equals("AI")){
                _difficulty.setVisible(true);
                _AIMode.setVisible(true);
                _continueButton.setVisible(false);
                _usernameTextField.setVisible(true);
                _enteredUsername.setVisible(true);

                _hostIP.setVisible(true);
                _hostIPInput.setVisible(true);
            } else{
                _difficulty.setVisible(false);
                _AIMode.setVisible(false);
                _enteredUsername.setVisible(true);

                _hostIP.setVisible(true);
                _hostIPInput.setVisible(true);
            }
        });

        _AIGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            _selectedDifficulty = _AIGroup.getSelectedToggle().getUserData().toString();
            if (_AIGroup.getSelectedToggle() != null){
                _enteredUsername.setVisible(true);
                _usernameTextField.setVisible(true);
                _continueButton.setVisible(true);

                _hostIP.setVisible(true);
                _hostIPInput.setVisible(true);
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

    public String getHostname() {
        return _hostIPInput.getText();
    }

    public String getGameMode(){
        return _selectedGameMode;
    }

    public String getDifficulty(){
        return _selectedDifficulty;
    }
}
