package com.rockingstar.engine.command.server;

public class ResponseHandler {

    private String _message;

    public ResponseHandler() {

    }

    public void handle(String response) {
        String responseType = response.split(" ")[0];

        switch(responseType){
            case "OK":
                _message = "OK";
                break;
            case "ERR":
                _message = response.substring(4);
                break;
            case "SVR":
                handleSVR(responseType);
        }
    }

    public void handleSVR(String response){
        response = response.split(" ")[0];
        //@TODO doe wat met de responses;
        switch(response){
            case "HELP":
                System.out.println("help");
                break;
            case "PLAYERLIST":
                System.out.println("show playerlist: " + response);
                break;
            case "GAMELIST":
                System.out.println("show gamelist: " + response);
                break;
            case "GAME":
                response = response.substring(5);
                response = response.split(" ")[0];
                switch(response){
                    case "MATCH":
                        System.out.println("Toewijzing van een match");
                    case "YOURTURN":
                        System.out.println("Toewijzing van de beurt tijdens de match");
                    case "MOVE":
                        System.out.println("Een zet gedaan tijdens de match");
                    case "CHALLENGE":
                        System.out.println("Bericht met betrekking op een uitdaging");
                    case "WIN":
                        System.out.println("Ontvanger heeft spel gewonnen");
                    case "LOSS":
                        System.out.println("Ontvanger heeft spel verloren");
                    case "DRAW":
                        System.out.println("Match is geeindigd in gelijk spel");
                }
        }
    }

    public String getMessage() {
        return _message;
    }
}
