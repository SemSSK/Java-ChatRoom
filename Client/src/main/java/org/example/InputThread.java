package org.example;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class InputThread extends Thread{

    private ObjectInputStream in;
    private List<Message> messages;
    private List<String> clients;

    public InputThread(ObjectInputStream in,List<Message> messages,List<String> clients){
        this.in = in;
        this.messages = messages;
        this.clients = clients;
    }

    private void updateClientList(List<String> clients){
        this.clients.clear();
        this.clients.addAll(clients);
    }

    private void updateMessages(Message msg){
        this.messages.add(msg);
    }

    private void onMessage(Object object){
        MessageHandler messageHandler = new MessageHandler((Message) object);
        switch (messageHandler.getType()){
            case ClientsList -> updateClientList((List<String>) messageHandler.getContent());
            case TextMessage -> updateMessages(messageHandler.getMessage());
        }
    }

    @Override
    public void run(){
        while(true) {
            try {
                 onMessage(in.readObject());
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }
    }
}