package org.example;


import java.io.IOException;
import java.io.ObjectInputStream;

class InputThread extends Thread{
    private ObjectInputStream in;

    public InputThread(ObjectInputStream in){
        this.in = in;
    }
    @Override
    public void run(){
        while(true) {
            String received = null;
            try {
                received = (String) in.readObject();
                HelperMethods.log(received);
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }
    }
}