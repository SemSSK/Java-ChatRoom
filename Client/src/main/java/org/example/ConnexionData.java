package org.example;

import imgui.type.ImInt;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnexionData {
    private ImInt id = new ImInt();
    private ObjectOutputStream out = null;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<String> chatMembers = new ArrayList<>();

    public ConnexionData(){};

    public void load(Socket socket) throws IOException {
        id.set(socket.getPort());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    public ConnexionData(ObjectOutputStream out, List<Message> messages, List<String> chatMembers){
        this.out = out;
        this.messages = (ArrayList<Message>) messages;
        this.chatMembers = (ArrayList<String>) chatMembers;
    }

    public synchronized void onMessage(Object object){
        Message message = (Message) object;
        MessageHandler messageHandler = new MessageHandler((Message) object);
        switch (messageHandler.getType()){
            case TextMessage -> addMessages(message);
            case ClientsList -> setChatMembers((List<String>) message.getContent());
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public synchronized void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public synchronized  void addMessages(Message message){
        this.messages.add(message);
    }

    public ArrayList<String> getChatMembers() {
        return chatMembers;
    }

    public synchronized void setChatMembers(List<String> chatMembers) {
        this.chatMembers = (ArrayList<String>) chatMembers;
    }

    public synchronized void sendMessage(Message message) throws IOException {
        out.writeObject(message);
    }
}
