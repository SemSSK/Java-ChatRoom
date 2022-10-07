package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket client;
    final static String message = "Merci pour l'envoi";
    ServerThread (Socket client){
        this.client = client;
    }
    static ArrayList<ObjectOutputStream> outs = new ArrayList<>();

    synchronized void addOutput(ObjectOutputStream out){
        outs.add(out);
    }
    synchronized void deleteOutput(ObjectOutputStream out){
        outs.remove(out);
    }

    @Override
    public void run(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            addOutput(out);
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            HelperMethods.log("IO streams créer");
            HelperMethods.log("Client connected successfully Ip is : " + client.getInetAddress());
            Thread ioT = new IOThread(in,outs);
            ioT.start();
            ioT.join();
            deleteOutput(out);
            HelperMethods.log("Fermeture I/O streams");
            HelperMethods.log("Fermeture de la connection");
            Thread.sleep(30000);
        } catch (IOException e) {
        }  catch (InterruptedException e) {
        }
    }

}
