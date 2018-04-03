package com.rockingstar.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Bert de Boer on 3/27/2018.
 */

public class ServerConnection {

    private static ServerConnection uniqueInstance;

    private ServerConnection() {
        String data = "login";
        String logout = "disconnect";
        try {
            Socket sock = new Socket("localhost", 8000);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            out.print(data);
            //out.print(logout);

            //Thread.sleep(5000000);
            out.close();

            System.out.println("Data sent");
            while (!in.ready()) {
            }
            String input = in.readLine();
            System.out.println(input);

        }
        catch(IOException e){
            System.out.println("Connection Failed: " + e);
        }
    }

    public static ServerConnection getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ServerConnection();
        }
        return uniqueInstance;
    }


}

