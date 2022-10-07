package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class IOThread extends Thread{

    private ObjectInputStream in;
    private List<ObjectOutputStream> outs;

    public IOThread(ObjectInputStream in,List<ObjectOutputStream> outs){
        this.in = in;
        this.outs = outs;
    }

    private synchronized void sendOutputs(String m){
        outs.forEach(out-> {
            try {
                out.writeObject(m);
            } catch (IOException e) {}
        });
    }

    @Override
    public void run(){
        while(true) {
            String received = null;
            try {
                received = (String) in.readObject();
                HelperMethods.log(received);
                sendOutputs(received);
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }
    }

}
