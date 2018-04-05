package com.rockingstar.engine.command.server;

import com.rockingstar.engine.game.AbstractGame;
import com.rockingstar.engine.io.models.Util;
import com.rockingstar.engine.lobby.controllers.Launcher;

/**
 * Created by Bert de Boer on 4/5/2018.
 */
public class GameEventHandler extends ReceivedMessageHandler{

    private String[] parameters;
    private String output;

    private AbstractGame _game;

    public GameEventHandler(String message){
        super(message);
        _game = Launcher.getInstance().getGame();
        handle();
    }

    private void parse(){
        parameters = message.split(" ");
    }

    private void handle() {
        parse();

        String thingie = message.substring(parameters[0].length());

        switch (parameters[0].toLowerCase()) {
            case "help":
                System.out.println(thingie);
                break;
            case "playerlist":
                for (String string : parseFakeCollection(thingie)) {
                    System.out.println(string);
                }

                break;
            default:
                Util.displayStatus("Parsing command " + parameters[0], false);
        }
    }

    @Override
    public String toString(){
        return output;
    }
}
