package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        ClientThread t1 = new ClientThread();
        //ClientThread t2 = new ClientThread();
        t1.start();
        //t2.start();
        try {
            t1.join();
            //t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}