package com.rockingstar.engine;

import com.rockingstar.engine.command.Command;
import com.rockingstar.engine.io.models.Util;

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

    private Socket _socket;
    private PrintWriter _out;
    private BufferedReader _in;

    private Thread _loop;

    private final Boolean _isReading = true;

    private ServerConnection() {
        try {
            _socket = new Socket("localhost", 7789);
            _out = new PrintWriter(_socket.getOutputStream(), true);
            _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));

            Util.displayStatus("Established server connection");

            while (!_in.ready());
        }
        catch(IOException e) {
            System.out.printf("Could not connect to server: %s\n", e.toString());
            Util.exit("Establishing server connection...");
        }
    }

    public String send(String command) {
        synchronized (_isReading) {
            _out.println(command);
            System.out.println("Hi there");
        }

        return receive();
    }

    public String receive() {
        String message;
        boolean result = true;

        try {
            message = _in.readLine();
        }
        catch (IOException e) {
            result = false;
            return null;
        }
        finally {
            Util.displayStatus("Receiving message", result);
        }

        return message;
    }

    public void readAll() {
        _loop = new Thread(() -> {
            while (true) {
                synchronized (_isReading) {
                    try {
                        System.out.println(_in.readLine());
                        _isReading.notify();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        _loop.start();
    }

    public void close() {
        boolean result = true;

        try {
            _in.close();
            _out.close();
            _socket.close();
        }
        catch (IOException e) {
            result = false;
        }
        finally {
            Util.displayStatus("Disconnecting from server", result);
        }
    }

    public static ServerConnection getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new ServerConnection();
        }

        return uniqueInstance;
    }
}

