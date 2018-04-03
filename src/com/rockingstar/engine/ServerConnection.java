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
        String data = "login";
        String logout = "disconnect";

        try {
            Socket s = new Socket("localhost", 8000);
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter a message");
            String clientMessage = in.nextLine();

//Make a printwriter and write the message to the socket
            PrintWriter writer = new PrintWriter(s.getOutputStream());
            writer.println(clientMessage); // <- println
            writer.flush();                // <- flush

//StreamReader to read the response from the server
            InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

//Get the response message and print it to console
            String responseMessage = reader.readLine();
            System.out.println(responseMessage);
            reader.close();
            writer.close();                // <- new location for close (*)



            /*Socket sock = new Socket("localhost", 8000);
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
*/
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

