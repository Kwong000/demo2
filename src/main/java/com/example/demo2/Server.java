package com.example.demo2;

public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocketConnector connector = new ServerSocketConnector();
        Thread connectorThread = new Thread(connector);
        connectorThread.start();
    }
}