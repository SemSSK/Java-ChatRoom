package org.example;

import org.example.View.GUI;

public class Main {
    static final private ConnexionData clientInfo = new ConnexionData();
    static final private AppState appState = new AppState();

    public static void main(String[] args) {
        GUI gui = new GUI(appState,clientInfo);
        GUI.launch(gui);
    }

    public static void enterRoom(){
        ClientThread t1 = new ClientThread(appState,clientInfo);
        t1.start();
    }

}