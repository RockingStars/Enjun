package com.rockingstar.engine.game;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Timer extends Thread {

    private Label _countLabel;

    public Timer(Label countLabel) {
        _countLabel = countLabel;
    }

    /**
     * Method that sets a timer to keep track of the time left to make a move
     */
    @Override
    public void run(){
        int counter = 10;

        while (counter >= 0 && !interrupted()) {
            final int finalCounter = counter;

            Platform.runLater(() -> _countLabel.setText("" + finalCounter));
            System.out.println("Hierzo: " + counter);
            counter = counter - 1;

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
