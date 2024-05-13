package com.example.demo2;

import java.io.IOException;
import java.io.ObjectInputStream;

public class DataReader implements Runnable {
    ObjectInputStream objIn;
    MyCoolDataStructure inData;

    public DataReader(ObjectInputStream objIn, MyCoolDataStructure inData) throws Exception {
        this.inData = inData;
        this.objIn = objIn;
    }

    public void run()  {
        while (true) {
            try {
                Object inMessage1 = objIn.readObject();
                inData.put(inMessage1);
            } catch (IOException ioex) {
                // its ok to get IOException when there is no object to read in from ObjectInputStream
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}