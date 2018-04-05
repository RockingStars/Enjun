package com.rockingstar.engine.command.server;

/**
 * Created by Bert de Boer on 4/5/2018.
 */
public class GameEventHandler extends ReceivedMessageHandler{

    private String[] parameters;
    private String output;

    public GameEventHandler(String message){
        super(message);
    }

    private void parse(){
        parameters = message.substring(0,3).split(" ");
    }

    @Override
    public String toString(){
        return output;
    }

}
