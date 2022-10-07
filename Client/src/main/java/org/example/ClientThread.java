package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread{

    final static String HOST = "127.0.0.1";
    String pseudo;
    int PORT = 3000;

    public ClientThread(String pseudo,int PORT){
        this.pseudo = pseudo;
        this.PORT = PORT;
    }


    @Override
    public void run(){
        try {
            Socket server = new Socket(HOST,PORT);
            HelperMethods.log("Connected to server");
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            Main.setOutputStream(out);
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            HelperMethods.log("Created streams");
            Thread inT = new InputThread(in);
            //Thread outT = new OutputThread(out,pseudo);
            inT.start();
            //outT.start();
            inT.join();
            //outT.join();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
    }


}
