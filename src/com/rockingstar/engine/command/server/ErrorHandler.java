package com.rockingstar.engine.command.server;

/**
 * Created by Bert de Boer on 4/5/2018.
 */
public class ErrorHandler {

    private String error;

    public ErrorHandler(String error){
        this.error = error;
    }

    public String getError(){
        return error;
    }

}
