package org.example;

import imgui.type.ImInt;
import imgui.type.ImString;
import org.example.View.GUI;
import org.example.View.GuiState;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {
    static ImString pseudo = new ImString("client");
    static ImInt PORT = new ImInt(3000);
    static ImString IP = new ImString("127.0.0.1");
    static ObjectOutputStream out = null;
    static ArrayList<Message> messages = new ArrayList<>();
    static ArrayList<String> currentClients = new ArrayList<>();
    static private AppState appState = new AppState();

    public static void main(String[] args) {
        GUI gui = new GUI(PORT,IP,pseudo,messages,currentClients,appState);
        GUI.launch(gui);
    }

    public static void enterRoom(){
        ClientThread t1 = new ClientThread(pseudo,IP,PORT,messages,currentClients);
        appState.setState(GuiState.Started);
        t1.start();
    }

    public static void sendMessage(Message m) throws IOException {
        out.writeObject(m);
    }
    public static void setOutputStream(ObjectOutputStream outputStream){
        out = outputStream;
    }

    public static void setToFailed() {
        appState.setState(GuiState.Failed);
    }
}