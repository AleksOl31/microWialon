package ru.alexanna.microwialon.network;

import ru.alexanna.microwialon.wialonips.packettypes.IpsPacket;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerConnection {

    private final String HOST =  /*"10.70.0.160"; */  "82.151.104.72";
    private final int PORT = 20332;

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public ServerConnection() {
        try {
            socket = new Socket(HOST, PORT);
            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during a connection to the server.", e);
        }
    }

    public void sendMessage(IpsPacket ipsPack) {
        try {
            out.write(ipsPack.toString());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during sending message to the server.", e);
        }
    }

    public String receiveMessage() {
        try {
            String response = in.readLine();
            return response;
        } catch (IOException e) {
//            throw new RuntimeException("Something went wrong during receiving message from the server.", e);
            return null;
        }
    }

    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
            System.out.println("Socket closed on: \t" + new Date());
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during closing connection.", e);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }
}
