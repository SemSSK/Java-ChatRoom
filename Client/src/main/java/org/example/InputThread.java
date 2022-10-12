package org.example;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

class InputThread extends Thread{

    private ObjectInputStream in;
    private ConnexionData clientInfo;
    private boolean running = true;

    public InputThread(ObjectInputStream in, ConnexionData clientInfo){
        this.in = in;
        this.clientInfo = clientInfo;
    }

    @Override
    public void run(){
        while(running) {
            try {
                 clientInfo.onMessage(in.readObject());
            } catch (IOException e) {
                running = false;
            } catch (ClassNotFoundException e) {
            }
        }
    }
}