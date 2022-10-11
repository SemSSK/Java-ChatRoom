package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class IOThread extends Thread{

    private ObjectInputStream in;
    private List<ObjectOutputStream> outs;
    private List<Message> messages;

    public IOThread(ObjectInputStream in,List<ObjectOutputStream> outs,List<Message> messages){
        this.in = in;
        this.outs = outs;
        this.messages = messages;
    }

    private void onMessage(Object object){
        MessageHandler messageHandler = new MessageHandler((Message) object);
        switch (messageHandler.getType()){
            case TextMessage -> {
                messages.add(messageHandler.getMessage());
                sendOutputs(object);
                HelperMethods.log(messageHandler.getMessage().getPseudo() + ":" + messageHandler.getMessage().getContent());
            }
        }
    }


    private synchronized void sendOutputs(Object o){
        outs.forEach(out-> {
            try {
                out.writeObject(o);
            } catch (IOException e) {}
        });
    }

    @Override
    public void run(){
        boolean connected = true;
        while(connected)
            try {
                onMessage(in.readObject());
            } catch (IOException e) {
                connected = false;
            } catch (ClassNotFoundException e) {
            }
        }
}
