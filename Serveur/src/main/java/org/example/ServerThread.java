package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerThread extends Thread{

    private Socket client;
    final static String message = "Merci pour l'envoi";
    static ArrayList<ObjectOutputStream> outs = new ArrayList<>();
    public Map<Integer,String> connectedClients;
    private List<Message> messages;
    synchronized void addOutput(ObjectOutputStream out){
        outs.add(out);
    }
    synchronized void deleteOutput(ObjectOutputStream out){
        outs.remove(out);
    }

    ServerThread (Socket client,List<Message> messages, Map<Integer,String> connectedClients){
        this.client = client;
        this.messages = messages;
        this.connectedClients = connectedClients;
    }

    private void registerClient(int id,String pseudo){
        connectedClients.put(id,pseudo);
    }
    private void sendClientsList(){
        List<String> clients = connectedClients.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
        outs.forEach(out -> {
            try {
                out.writeObject(new Message(0,null,clients));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            addOutput(out);
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            registerClient(client.getPort(),((Message)in.readObject()).getPseudo());
            sendClientsList();
            HelperMethods.log("Client connected successfully Ip is : " + client.getInetAddress());
            Thread ioT = new IOThread(in,outs,messages);
            ioT.start();
            ioT.join();
            deleteOutput(out);
            connectedClients.remove(client.getPort());
            sendClientsList();
            System.out.println("Client left");
        } catch (IOException e) {
        }  catch (InterruptedException e) {
        } catch (ClassNotFoundException e) {
        }
    }


}
