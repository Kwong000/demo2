package com.example.demo2;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketConnector implements Runnable {
    MyCoolDataStructure queue;
    Socket actualSocket;
    int clientCount;

    public void run() {
        queue = new MyCoolDataStructure();
        ServerSocket mySocket;
        try {
            mySocket = new ServerSocket(3256);
        } catch (Exception ex) {
            System.out.println("Server failed: " + ex);
            return;
        }

        while (true) {
            try {
                System.out.println("Server socket at IP 10.37.157.240 port 3256");
                System.out.println("... waiting for connectionSSS");
                actualSocket = mySocket.accept();
                clientCount = clientCount + 1;
                System.out.println("Client #" + clientCount + " CONNECTED!");

                OutputStream out = actualSocket.getOutputStream();
                ObjectOutputStream objOut = new ObjectOutputStream(out);
                InputStream in = actualSocket.getInputStream();
                ObjectInputStream objIn = new ObjectInputStream(in);

                DataReader myDataReader = new DataReader(objIn, queue);
                ProgramLogicDoer myProgramLogicDoer = new ProgramLogicDoer(queue, objOut, null, true);
                Thread dataReadThread = new Thread(myDataReader);
                Thread programLogicThread = new Thread(myProgramLogicDoer);
                dataReadThread.start();
                programLogicThread.start();

                objOut.writeObject("Mr. Hernandez says: Welcome to our chat!  Send and receive messages TO and FROM everyone.");
            } catch (Exception ex) {
                System.out.println("Server connection failed: "+ ex);
            }
        }
    }

}