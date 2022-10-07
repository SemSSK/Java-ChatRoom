package org.example;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

class OutputThread extends Thread{
    private ObjectOutputStream out;
    private String pseudo;

    public OutputThread(ObjectOutputStream out,String pseudo){
        this.out = out;
        this.pseudo = pseudo;
    }

    @Override
    public void run(){
        while(true) {
            Scanner s = new Scanner(System.in);
            String message = s.nextLine();
            try {
                out.writeObject(pseudo + ":" + message);
            } catch (IOException e) {
            }
        }
    }
}