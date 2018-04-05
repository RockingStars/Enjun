package com.rockingstar.engine.command.server;

public class ResponseHandler {

    public ResponseHandler() {
    }

    public void handle(String response) {
        String fullResponse = response;
        switch(response.substring(0,4)){
            case "OK": System.out.println("Ok"); break;
            case "ERR": System.out.println("Error occured");  break;
                //new ErrorHandler(fullResponse);
                // @TODO vang errors op
            case "SVR": new GameEventHandler(response.substring(4)); break;
        }
    }
}
