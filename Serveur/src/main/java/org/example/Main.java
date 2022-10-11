package org.example;

import imgui.type.ImInt;
import org.example.View.GUI;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static ImInt PORT = new ImInt(3000);
    static List<Message> messages = new ArrayList<>();
    static Map<Integer,String> connectedClients = new HashMap<>();

    public static void main(String[] args) {
        GUI gui = new GUI(PORT,messages,connectedClients);
        GUI.launch(gui);
    }

    public static void startServer(){
        try {
            HelperMethods.log("Starting server");
            ServerSocket server = new ServerSocket(PORT.get());
            HelperMethods.log("Server started on port : " + PORT);
            while(true){
                ServerThread client = new ServerThread(server.accept(),messages,connectedClients);
                client.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}