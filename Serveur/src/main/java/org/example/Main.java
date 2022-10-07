package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    final static int PORT = 3000;
    public static void main(String[] args) {
        try {
            log("Starting server");
            ServerSocket server = new ServerSocket(PORT);
            log("Server started on port : 3000");

            while(true){
                ServerThread client = new ServerThread(server.accept());
                client.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void log(String s){
        System.out.println(s);
    }

    static String inputStreamConverter(InputStream stream) throws IOException {
        return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
    }

}