package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread{

    final static String HOST = "127.0.0.1";
    String pseudo;
    final static int PORT = 3000;

    private void setPseudo(){
        Scanner s = new Scanner(System.in);
        pseudo = s.nextLine();
    }

    @Override
    public void run(){
        setPseudo();
        try {
            Socket server = new Socket(HOST,PORT);
            HelperMethods.log("Connected to server");
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            HelperMethods.log("Created streams");
            Thread inT = new InputThread(in);
            Thread outT = new OutputThread(out,pseudo);
            inT.start();
            outT.start();
            inT.join();
            outT.join();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
    }


}
