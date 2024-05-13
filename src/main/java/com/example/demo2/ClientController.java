package com.example.demo2;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientController {
    public TextField messageTyped;
    public ListView allMessages;

    MyCoolDataStructure queue;
    ObjectOutputStream objOut;
    ObjectInputStream objIn;

    public void initialize()  {
        try {
            System.out.println("Connecting to my server");
            Socket newSocket = new Socket("10.37.157.240", 3256);
            OutputStream out = newSocket.getOutputStream();
            objOut = new ObjectOutputStream(out);
            InputStream in = newSocket.getInputStream();
            objIn = new ObjectInputStream(in);
            queue = new MyCoolDataStructure();
            DataReader myDataReader = new DataReader(objIn, queue);
            ProgramLogicDoer myProgramLogicDoer = new ProgramLogicDoer(queue, objOut, this, false);
            Thread dataReadThread = new Thread(myDataReader);
            Thread programLogicThread = new Thread(myProgramLogicDoer);
            dataReadThread.start();
            programLogicThread.start();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void sendMessage() throws Exception {
        String message = messageTyped.getText();
        messageTyped.clear();
        objOut.writeObject(message);
    }
}