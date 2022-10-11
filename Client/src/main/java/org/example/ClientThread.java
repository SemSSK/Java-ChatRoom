package org.example;

import imgui.type.ImInt;
import imgui.type.ImString;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientThread extends Thread{

    ImString HOST;
    ImString pseudo;
    ImInt PORT;
    List<Message> messages;
    List<String> clients;

    public ClientThread(ImString pseudo,ImString HOST ,ImInt PORT,List<Message> messages,List<String> clients){
        this.pseudo = pseudo;
        this.HOST = HOST;
        this.PORT = PORT;
        this.messages = messages;
        this.clients = clients;
    }


    @Override
    public void run(){
        try {
            Socket server = new Socket(HOST.get(),PORT.get());
            HelperMethods.log("Connected to server");
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            out.writeObject(new Message(0, pseudo.get(), ""));
            Main.setOutputStream(out);
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            HelperMethods.log("Created streams");
            Thread inT = new InputThread(in,messages,clients);
            //Thread outT = new OutputThread(out,pseudo);
            inT.start();
            //outT.start();
            inT.join();
            //outT.join();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
        Main.setToFailed();
    }


}
