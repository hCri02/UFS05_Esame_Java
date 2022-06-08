package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP
{
    static int portNumber = 1234;
    static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;

    public static void main(String[] args) {

        serverSocket = openServer();
        if (serverSocket == null) { return; }

        System.out.println("Server TCP started!");

        while (true) {
            clientSocket = openClientSocket();
            ClientTCPHandler clientHandler = new ClientTCPHandler(clientSocket);
            clientHandler.handle();
            closeClientSocket();
        }
    }

    private static ServerSocket openServer() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } return serverSocket;
    }

    private static Socket openClientSocket() {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Server accepted");
        } catch (IOException e) {
            System.out.println("Accept failed" + e);
            return null;
        } return clientSocket;
    }

    private static void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



