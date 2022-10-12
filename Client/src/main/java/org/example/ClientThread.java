package org.example;

import org.example.View.GuiState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread{
    private AppState appState;
    private ConnexionData clientInfo;

    public ClientThread(AppState appState, ConnexionData clientInfo){
        this.appState = appState;
        this.clientInfo = clientInfo;
    }


    @Override
    public void run(){
        try {
            Socket server = new Socket(appState.getIp().get(),appState.getPort().get());
            appState.setState(GuiState.Started);
            clientInfo.load(server);
            clientInfo.sendMessage(new Message(0, appState.getPseudo().get(),""));
            HelperMethods.log("Connected to server");
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            Thread inT = new InputThread(in,clientInfo);
            inT.start();
            inT.join();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
        appState.setState(GuiState.Failed);
    }


}
