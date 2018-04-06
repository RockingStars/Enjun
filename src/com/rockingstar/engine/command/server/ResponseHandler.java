package com.rockingstar.engine.command.server;

public class ResponseHandler {

    public ResponseHandler() {

    }

    public void handle(String response) {
        response = response.split(" ")[0];
        switch(response){
            case "OK": System.out.println("Ok"); break;
            case "ERR": System.out.println("An error has occurred");  break;
                //new ErrorHandler(fullResponse);
                // @TODO vang errors op
            case "SVR":
                handleSVR(response);
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






}
