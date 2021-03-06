package com.rockingstar.engine;

import com.rockingstar.engine.command.server.ResponseHandler;
import com.rockingstar.engine.io.models.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Rocking Stars
 * @since  beta 1.0
 */
public class ServerConnection extends Thread {

    private static ServerConnection uniqueInstance;

    private Socket _socket;
    private ResponseHandler _handler;


    /**
     * Tries to connect to the server via socket, if not possible prints error message
     */
    private ServerConnection(String hostname, int port) throws IOException {
        //_socket = new Socket("145.33.225.170", 7789);
        _socket = new Socket(hostname, port);
        //_socket = new Socket("77.162.40.81", 7789);
        Util.displayStatus("Established server connection");
        _handler = new ResponseHandler();
    }

    /**
     * To check if you are connected
     * @return true while connected
     */
    private boolean connected(){
        return !_socket.isClosed();
    }

    /**
     * Creates new PrintWriter, prints command to the PrintWriter.
     * Displats command as status and wait for 0,1 seconds
     * @param command command to be entered to PrintWriters and status
     */
    public void send(String command) {
        try {
            PrintWriter output = new PrintWriter(_socket.getOutputStream(), true);
            output.println(command);
            Util.displayStatus("Client Command: " + command);
            Thread.sleep(100);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * While connected to server, put the response to the BufferedReader.
     * displays the response, and makes sure the response is handled
     * @throws IOException
     */
    private void receive() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        String response;
        while (connected()) {
            try {
                response = input.readLine();
            } catch (SocketException e){
                return;
            }
            if (response != null) {
                Util.displayStatus("Server Response: " + response);
                _handler.handle(response);
            }
        }
    }

    @Override
    public void run() {
        try {
            this.receive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to close the connection with the server
     */
    public void close() {
        try {
            _socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Util.displayStatus("Disconnecting from server");
        }
    }

    /**
     * checks if there is an serverconnection, if not, create a new ServerConnection
     * @return new Serverconnection
     */
    public static ServerConnection getInstance(){
        if (uniqueInstance == null)
            return null;

        return uniqueInstance;
    }

    public static ServerConnection getInstance(String hostname, int port) throws IOException {
        if(uniqueInstance == null){
            uniqueInstance = new ServerConnection(hostname, port);
        }

        return uniqueInstance;
    }

    /**
     * Gets response from Response Handler
     * @return the getMessage method from ResponseHandler
     */
    public String getResponse() {
        return _handler.getMessage();
    }

    /**
     * Checks if a command is a valid command
     * @return true if a command is valid, false if not
     */
    public boolean isValidCommand() {
        return _handler.isValidCommand();
    }
}
