package com.example.demo2;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    static MyCoolDataStructure queue;
    public static void main(String[] args) throws Exception {

        // Controller.initialize()
        System.out.println("Connecting to my server");
        Socket newSocket = new Socket("127.0.0.1",3256);
        OutputStream out = newSocket.getOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        InputStream in = newSocket.getInputStream();
        ObjectInputStream objIn = new ObjectInputStream(in);
        queue = new MyCoolDataStructure();
        DataReader myDataReader = new DataReader(objIn, queue);
        ProgramLogicDoer myProgramLogicDoer = new ProgramLogicDoer(queue, objOut, null, false);
        Thread dataReadThread = new Thread(myDataReader);
        Thread programLogicThread = new Thread(myProgramLogicDoer);
        dataReadThread.start();
        programLogicThread.start();

        // Controller.sendMessage()  onAction
        objOut.writeObject("HIIII");
    }
}