package com.rockingstar.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Bert de Boer on 3/27/2018.
 */

public class ServerConnection {

    private static ServerConnection uniqueInstance;

    private ServerConnection() {
        String data = "login Robert";
        String logout = "disconnect";

        try {
            Socket sock = new Socket("localhost", 7789);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            Util.displayStatus("Established server connection");

            out.println(data);

            Thread.sleep(5000000);

            System.out.println("Data sent");
            while (!in.ready());

            String input = in.readLine();
            System.out.println(input);

            // @todo Move to .close() method
            in.close();
            out.close();
            sock.close();
        }
        catch(IOException | InterruptedException e) {
            if (e instanceof IOException)
                System.out.printf("Could not connect to server: %s\n", e.toString());
            else if (e instanceof InterruptedException)
                System.out.printf("Connection was interrupted: %s\n", e.toString());
            else
                e.printStackTrace();

            Util.exit("Maintaining server connection...");
        }
    }

    public static ServerConnection getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ServerConnection();
        }

        return uniqueInstance;
    }


}

